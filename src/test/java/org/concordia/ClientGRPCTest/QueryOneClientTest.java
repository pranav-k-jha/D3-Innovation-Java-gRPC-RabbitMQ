package org.concordia.ClientGRPCTest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import proto.EduCostStat.QueryOneRequest;
import proto.EduCostStat.QueryOneResponse;
import proto.EduCostStatServiceGrpc;

public class QueryOneClientTest {

	private static final Logger logger = Logger.getLogger(QueryOneClientTest.class.getName());

	private final ManagedChannel channel;
	private final EduCostStatServiceGrpc.EduCostStatServiceBlockingStub blockingStub;

	public QueryOneClientTest(String host, int port) {
		channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
		blockingStub = EduCostStatServiceGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void queryOne(int year, String state, String type, String length, String expense) {
		logger.info("QueryOne: year=" + year + ", state=" + state + ", type=" + type + ", length=" + length
				+ ", expense=" + expense);
		QueryOneRequest request = QueryOneRequest.newBuilder().setYear(year).setState(state).setType(type)
				.setLength(String.valueOf(length)).setExpense(String.valueOf(expense)).build();
		QueryOneResponse response;
		try {
			response = blockingStub.queryOne(request);
			logger.info("QueryOne Response: " + response.getQueryId());
		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		QueryOneClientTest client = new QueryOneClientTest("localhost", 50051);
		try {
			// Sample query parameters
			int year = 2014;
			String state = "New York";
			String type = "Public";
			String length = "4-year";
			String expense = "Fees/Tuition";
			//
			year = 2021;
			state = "Arkansas";
			type = "Public In-State";
			length = "2-year";
			expense = "Fees/Tuition";

			client.queryOne(year, state, type, length, expense);
		} finally {
			client.shutdown();
		}
	}
}
