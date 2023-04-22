package org.concordia.ClientGRPCTest;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import proto.EduCostStat.QueryFourRequest;
import proto.EduCostStat.QueryFourResponse;
import proto.EduCostStatServiceGrpc;

public class QueryFourClientTest {

    private static final Logger logger = Logger.getLogger(QueryFourClientTest.class.getName());
    private final ManagedChannel channel;
    private final EduCostStatServiceGrpc.EduCostStatServiceBlockingStub blockingStub;

    public QueryFourClientTest(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public QueryFourClientTest(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = EduCostStatServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void queryFour(int baseYear, int yearsAgo, String type, String length) {
        logger.info("QueryFour: baseYear=" + baseYear + ", yearsAgo=" + yearsAgo + ", type=" + type + ", length=" + length);
        QueryFourRequest request = QueryFourRequest.newBuilder().setBaseYear(baseYear).setYearsAgo(yearsAgo)
                .setType(type).setLength(length).build();

        try {
            QueryFourResponse response = blockingStub.queryFour(request);
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

    public static void main(String[] args) throws Exception {
        QueryFourClientTest client = new QueryFourClientTest("localhost", 50051);
        try {
            int baseYear = 2021;
            int yearsAgo = 5;
            String type = "Public In-State";
            String length = "2-year";
            client.queryFour(baseYear, yearsAgo, type, length);
        } finally {
            client.shutdown();
        }
    }
}
