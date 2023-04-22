package org.concordia.ClientGRPCTest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import proto.EduCostStat.QueryFiveRequest;
import proto.EduCostStat.QueryFiveResponse;
import proto.EduCostStat.QueryFiveResponse.RegionCost;
import proto.EduCostStatServiceGrpc;

public class QueryFiveClientTest {
	private static final Logger logger = Logger.getLogger(QueryFiveClientTest.class.getName());
	private final ManagedChannel channel;
	private final EduCostStatServiceGrpc.EduCostStatServiceBlockingStub blockingStub;
	private final EduCostStatServiceGrpc.EduCostStatServiceStub asyncStub;

	public QueryFiveClientTest(String host, int port) {
		this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
	}

	public QueryFiveClientTest(ManagedChannelBuilder<?> channelBuilder) {
		channel = channelBuilder.build();
		blockingStub = EduCostStatServiceGrpc.newBlockingStub(channel);
		asyncStub = EduCostStatServiceGrpc.newStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
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
		QueryFiveClientTest client = new QueryFiveClientTest("localhost", 50051);
		try {
			int year = 2021;
			String type = "Private";
			String length = "4-year";
			client.queryFive(year, type, length);
		} finally {
			client.shutdown();
		}
	}
}
