package org.concordia.ClientGRPCTest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import proto.EduCostStat.EconomicState;
import proto.EduCostStat.QueryThreeRequest;
import proto.EduCostStat.QueryThreeResponse;
import proto.EduCostStatServiceGrpc;

public class QueryThreeClientTest {
	private static final Logger logger = Logger.getLogger(QueryThreeClientTest.class.getName());
	private final ManagedChannel channel;
	private final EduCostStatServiceGrpc.EduCostStatServiceBlockingStub blockingStub;

	public QueryThreeClientTest(String host, int port) {
		this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
	}

	public QueryThreeClientTest(ManagedChannelBuilder<?> channelBuilder) {
		channel = channelBuilder.build();
		blockingStub = EduCostStatServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void queryThree(int year, String type, String length) {
		logger.info("QueryThree: year=" + year + ", type=" + type + ", length=" + length);
		QueryThreeRequest request = QueryThreeRequest.newBuilder().setYear(year).setType(type).setLength(length)
				.build();
		QueryThreeResponse response;
		try {
			response = blockingStub.queryThree(request);
			logger.info("QueryThree Response:");
			for (EconomicState state : response.getEconomicStatesList()) {
				logger.info("State: " + state.getState() + ", Total: " + state.getTotal());
			}
		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
	}

	public static void main(String[] args) throws Exception {
		QueryThreeClientTest client = new QueryThreeClientTest("localhost", 50051);
		try {
			int year = 2013;
			String type = "Private";
			String length = "4-year";
			client.queryThree(year, type, length);

			year = 2020;
			type = "Public In-State";
			length = "4-year";
			client.queryThree(year, type, length);

		} finally {
			client.shutdown();
		}
	}
}
