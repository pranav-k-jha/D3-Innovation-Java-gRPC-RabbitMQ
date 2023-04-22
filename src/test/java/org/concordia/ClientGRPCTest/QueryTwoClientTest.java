package org.concordia.ClientGRPCTest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.concordia.DAO.*;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import proto.EduCostStat.ExpensiveState;
import proto.EduCostStat.QueryTwoRequest;
import proto.EduCostStat.QueryTwoResponse;
import proto.EduCostStatServiceGrpc;

public class QueryTwoClientTest {
	private static final Logger logger = Logger.getLogger(QueryTwoClientTest.class.getName());
	private final ManagedChannel channel;
	private final EduCostStatServiceGrpc.EduCostStatServiceBlockingStub blockingStub;

	public QueryTwoClientTest(String host, int port) {
		this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
	}

	public QueryTwoClientTest(ManagedChannelBuilder<?> channelBuilder) {
		channel = channelBuilder.build();
		blockingStub = EduCostStatServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void queryTwo(int year, String type, String length) {
		logger.info("QueryTwo: year=" + year + ", type=" + type + ", length=" + length);
		QueryTwoRequest request = QueryTwoRequest.newBuilder().setYear(year).setType(type).setLength(length).build();
		QueryTwoResponse response;
		try {
			response = blockingStub.queryTwo(request);
			logger.info("QueryTwo Response:");
			for (ExpensiveState state : response.getExpensiveStatesList()) {
				logger.info("State: " + state.getState() + ", Total: " + state.getTotal());
			}
		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
	}

	public static void main(String[] args) throws Exception {
		QueryTwoClientTest client = new QueryTwoClientTest("localhost", 50051);
		try {
			int year = 2013;
			String type = "Private";
			String length = "4-year";
			client.queryTwo(year, type, length);

			year = 2020;
			type = "Public In-State";
			length = "4-year";
			QueryTwoDAO.query(year, type, length);

		} finally {
			client.shutdown();
		}
	}
}
