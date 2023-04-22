package org.concordia.GRPCServer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bson.Document;
import org.concordia.DAO.*;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import proto.EduCostStat.EconomicState;
import proto.EduCostStat.ExpensiveState;
import proto.EduCostStat.QueryFiveRequest;
import proto.EduCostStat.QueryFiveResponse;
import proto.EduCostStat.QueryFiveResponse.RegionCost;
import proto.EduCostStat.QueryFourRequest;
import proto.EduCostStat.QueryFourResponse;
import proto.EduCostStat.QueryOneRequest;
import proto.EduCostStat.QueryOneResponse;
import proto.EduCostStat.QueryThreeRequest;
import proto.EduCostStat.QueryThreeResponse;
import proto.EduCostStat.QueryTwoRequest;
import proto.EduCostStat.QueryTwoResponse;
import proto.EduCostStatServiceGrpc;

public class EduCostStatServer {
	private final int port;
	private final Server server;
	public static final int PORT = 50051;

	public EduCostStatServer(int port) throws IOException {
		this(ServerBuilder.forPort(port), port);
	}

	public EduCostStatServer(ServerBuilder<?> serverBuilder, int port) {
		this.port = port;
		EduCostStatServiceImpl serviceImpl = new EduCostStatServiceImpl();
		server = serverBuilder.addService(serviceImpl).build();
	}

	public void start() throws IOException {
		server.start();
		System.out.println("Grpc Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.err.println("*** shutting down gRPC server since JVM is shutting down");
			EduCostStatServer.this.stop();
			System.err.println("*** server shut down");
		}));
	}

	public void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		EduCostStatServer server = new EduCostStatServer(50051);
		server.start();
		server.blockUntilShutdown();
	}

	static class EduCostStatServiceImpl extends EduCostStatServiceGrpc.EduCostStatServiceImplBase {
		@Override
		public void queryOne(QueryOneRequest request, StreamObserver<QueryOneResponse> responseObserver) {
			double totalExpense = QueryOneDAO.query(request.getYear(), request.getState(), request.getType(),
					request.getLength(), request.getExpense());
			String queryId = UUID.randomUUID().toString();
			QueryOneResponse response = QueryOneResponse.newBuilder().setQueryId(queryId)
					.setTotalExpense((int) totalExpense).build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void queryTwo(QueryTwoRequest request, StreamObserver<QueryTwoResponse> responseObserver) {
			List<Document> expensiveStates = QueryTwoDAO.query(request.getYear(), request.getType(),
					request.getLength());

			// Build the response using the list of documents
			QueryTwoResponse.Builder responseBuilder = QueryTwoResponse.newBuilder();
			for (Document doc : expensiveStates) {
				String state = doc.getString("_id");
				int total = doc.getInteger("total");
				ExpensiveState expensiveState = ExpensiveState.newBuilder().setState(state).setTotal(total).build();
				responseBuilder.addExpensiveStates(expensiveState);
			}
			QueryTwoResponse response = responseBuilder.build();

			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void queryThree(QueryThreeRequest request, StreamObserver<QueryThreeResponse> responseObserver) {
			List<Document> economicStates = QueryThreeDAO.query(request.getYear(), request.getType(),
					request.getLength());

			// Check if the economicStates list is not empty
			if (economicStates != null && !economicStates.isEmpty()) {
				// Build the response using the list of documents
				QueryThreeResponse.Builder responseBuilder = QueryThreeResponse.newBuilder();
				for (Document doc : economicStates) {
					String state = doc.getString("_id");
					int total = doc.getInteger("total");
					EconomicState economicState = EconomicState.newBuilder().setState(state).setTotal(total).build();
					responseBuilder.addEconomicStates(economicState);
				}
				QueryThreeResponse response = responseBuilder.build();

				responseObserver.onNext(response);
			} else {
				// Handle the case when there's no data found for the given query
				responseObserver.onError(new RuntimeException("No data found for the given query."));
			}

			responseObserver.onCompleted();
		}

		@Override
		public void queryFour(QueryFourRequest request, StreamObserver<QueryFourResponse> responseObserver) {
			List<Document> queryResults = QueryFourDAO.query(request.getBaseYear(),
					request.getBaseYear() - request.getYearsAgo() + 1, request.getType(), request.getLength());
			QueryFourResponse.Builder responseBuilder = QueryFourResponse.newBuilder().setQueryId("Query Four Result");

			for (Document result : queryResults) {
				String state = result.getString("state");
				Double growthRate = result.getDouble("growthRate");

				if (state != null && growthRate != null) {
					QueryFourResponse.StateGrowthRate.Builder stateGrowthRateBuilder = QueryFourResponse.StateGrowthRate
							.newBuilder().setState(state).setGrowthRate(growthRate);
					responseBuilder.addStateGrowthRates(stateGrowthRateBuilder.build());
				}
			}

			QueryFourResponse response = responseBuilder.build();
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}

		@Override
		public void queryFive(QueryFiveRequest request, StreamObserver<QueryFiveResponse> responseObserver) {
			try {
				int year = request.getYear();
				String type = request.getType();
				String length = request.getLength();
				// Call the appropriate method to perform the query
				Map<String, Double> regionCostMap = QueryFiveDAO.query(year, type, length);

				// Build the QueryFiveResponse
				QueryFiveResponse.Builder responseBuilder = QueryFiveResponse.newBuilder();
				responseBuilder.setQueryId(UUID.randomUUID().toString());

				for (Map.Entry<String, Double> entry : regionCostMap.entrySet()) {
					RegionCost regionCost = RegionCost.newBuilder().setRegion(entry.getKey()).setCost(entry.getValue())
							.build();
					responseBuilder.addRegionCosts(regionCost);
				}

				QueryFiveResponse response = responseBuilder.build();

				// Send the response
				responseObserver.onNext(response);
				responseObserver.onCompleted();
			} catch (Exception e) {
				responseObserver.onError(Status.INTERNAL.withCause(e).withDescription("Query failed").asException());

			}

		}
	}
}
