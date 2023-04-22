package org.concordia.GRPCClient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import proto.EduCostStat.*;
import proto.EduCostStat.QueryFiveResponse.RegionCost;
import proto.EduCostStatServiceGrpc;
import proto.EduCostStatServiceGrpc.EduCostStatServiceStub;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.stub.StreamObserver;

public class EduCostStatClient {
	private static final Logger logger = Logger.getLogger(EduCostStatClient.class.getName());
	private final ManagedChannel channel;
	private final EduCostStatServiceGrpc.EduCostStatServiceBlockingStub blockingStub;
	private EduCostStatServiceStub asyncStub;

	public EduCostStatClient(String host, int port) {
		this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
	}

	public EduCostStatClient(ManagedChannel channel) {
		this.channel = channel;
		this.blockingStub = EduCostStatServiceGrpc.newBlockingStub(channel);
		this.asyncStub = EduCostStatServiceGrpc.newStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	public void queryOne(int year, String state, String type, String length, String expense) {
	    logger.info("QueryOne: year=" + year + ", state=" + state + ", type=" + type + ", length=" + length
	            + ", expense=" + expense);
	    QueryOneRequest request = QueryOneRequest.newBuilder().setYear(year).setState(state).setType(type)
	            .setLength(length).setExpense(expense).build();
	    QueryOneResponse response;
	    try {
	        response = blockingStub.queryOne(request);
	        logger.info("QueryOne Response: " + response.getQueryId() + ", Total Expense: " + response.getTotalExpense());
	    } catch (StatusRuntimeException e) {
	        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
	    }
	}


	public void queryTwo(int year, String type, String length) {
		logger.info("QueryTwo: year=" + year + ", type=" + type + ", length=" + length);
		QueryTwoRequest request = QueryTwoRequest.newBuilder().setYear(year).setType(type).setLength(length).build();
		QueryTwoResponse response;
		try {
			response = blockingStub.queryTwo(request);
			logger.info("QueryTwo Response: ");
			for (ExpensiveState expensiveState : response.getExpensiveStatesList()) {
				logger.info("State: " + expensiveState.getState() + ", Total: " + expensiveState.getTotal());
			}
		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
	}

	public void queryThree(int year, String type, String length) {
		logger.info("QueryThree: year=" + year + ", type=" + type + ", length=" + length);
		QueryThreeRequest request = QueryThreeRequest.newBuilder().setYear(year).setType(type).setLength(length)
				.build();
		QueryThreeResponse response;
		try {
			response = blockingStub.queryThree(request);
			logger.info("QueryThree Response:");
			for (EconomicState economicState : response.getEconomicStatesList()) {
				logger.info("State: " + economicState.getState() + ", Total: " + economicState.getTotal());
			}
		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
	}

	public void queryFour(int baseYear, int yearsAgo, String type, String length) {
		logger.info(
				"QueryFour: baseYear=" + baseYear + ", yearsAgo=" + yearsAgo + ", type=" + type + ", length=" + length);
		QueryFourRequest request = QueryFourRequest.newBuilder().setBaseYear(baseYear).setYearsAgo(yearsAgo)
				.setType(type).setLength(length).build();
		QueryFourResponse response;
		try {
			response = blockingStub.queryFour(request);
			StringBuilder builder = new StringBuilder();
			builder.append("QueryFour Response: ").append(response.getQueryId()).append("\n");
			for (QueryFourResponse.StateGrowthRate stateGrowthRate : response.getStateGrowthRatesList()) {
				builder.append("State: ").append(stateGrowthRate.getState()).append(", Growth Rate: ")
						.append(stateGrowthRate.getGrowthRate()).append("\n");
			}
			logger.info(builder.toString());
		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
	}

	public void queryFive(int year, String type, String length) {
		logger.info("QueryFive: year=" + year + ", type=" + type + ", length=" + length);
		QueryFiveRequest request = QueryFiveRequest.newBuilder().setYear(year).setType(type).setLength(length).build();

		try {
			StreamObserver<QueryFiveResponse> responseObserver = new StreamObserver<QueryFiveResponse>() {
				@Override
				public void onNext(QueryFiveResponse response) {
					logger.info("QueryFive Response: " + response.getQueryId());
					for (RegionCost regionCost : response.getRegionCostsList()) {
						logger.info("Region: " + regionCost.getRegion() + ", Cost: " + regionCost.getCost());
					}
				}

				@Override
				public void onError(Throwable throwable) {
					logger.log(Level.WARNING, "RPC failed: {0}", throwable.getMessage());
				}

				@Override
				public void onCompleted() {
					logger.info("QueryFive completed");
				}
			};

			asyncStub.queryFive(request, responseObserver);

		} catch (StatusRuntimeException e) {
			logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
		}
	}

	public static void main(String[] args) throws Exception {
		EduCostStatClient client = new EduCostStatClient("localhost", 50051);
		try {
			int year = 2018;
			String state = "Arizona";
			String type = "Private";
			String length = "4-year";
			String expense = "Fees/Tuition";
			client.queryOne(year, state, type, length, expense);

//			year = 2013;
//			type = "Private";
//			length = "4-year";
//			client.queryTwo(year, type, length);
//
			year = 2015;
			type = "Public Out-of-State";
			length = "4-year";
			client.queryThree(year, type, length);
//
//			int baseYear = 2021;
//			int yearsAgo = 5;
//			type = "Private";
//			length = "4-year";
//			client.queryFour(baseYear, yearsAgo, type, length);
////
//			year = 2021;
//			type = "Public In-State";
//			length = "4-year";
//			client.queryFive(year, type, length);
		} finally {
			client.shutdown();
		}
	}

}
