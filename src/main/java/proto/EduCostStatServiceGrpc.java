package proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * EduCostStatService provides methods for querying education cost statistics.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: EduCostStat.proto")
public final class EduCostStatServiceGrpc {

  private EduCostStatServiceGrpc() {}

  public static final String SERVICE_NAME = "proto.EduCostStatService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<proto.EduCostStat.QueryOneRequest,
      proto.EduCostStat.QueryOneResponse> getQueryOneMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryOne",
      requestType = proto.EduCostStat.QueryOneRequest.class,
      responseType = proto.EduCostStat.QueryOneResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.EduCostStat.QueryOneRequest,
      proto.EduCostStat.QueryOneResponse> getQueryOneMethod() {
    io.grpc.MethodDescriptor<proto.EduCostStat.QueryOneRequest, proto.EduCostStat.QueryOneResponse> getQueryOneMethod;
    if ((getQueryOneMethod = EduCostStatServiceGrpc.getQueryOneMethod) == null) {
      synchronized (EduCostStatServiceGrpc.class) {
        if ((getQueryOneMethod = EduCostStatServiceGrpc.getQueryOneMethod) == null) {
          EduCostStatServiceGrpc.getQueryOneMethod = getQueryOneMethod = 
              io.grpc.MethodDescriptor.<proto.EduCostStat.QueryOneRequest, proto.EduCostStat.QueryOneResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.EduCostStatService", "QueryOne"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryOneRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryOneResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EduCostStatServiceMethodDescriptorSupplier("QueryOne"))
                  .build();
          }
        }
     }
     return getQueryOneMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.EduCostStat.QueryTwoRequest,
      proto.EduCostStat.QueryTwoResponse> getQueryTwoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryTwo",
      requestType = proto.EduCostStat.QueryTwoRequest.class,
      responseType = proto.EduCostStat.QueryTwoResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.EduCostStat.QueryTwoRequest,
      proto.EduCostStat.QueryTwoResponse> getQueryTwoMethod() {
    io.grpc.MethodDescriptor<proto.EduCostStat.QueryTwoRequest, proto.EduCostStat.QueryTwoResponse> getQueryTwoMethod;
    if ((getQueryTwoMethod = EduCostStatServiceGrpc.getQueryTwoMethod) == null) {
      synchronized (EduCostStatServiceGrpc.class) {
        if ((getQueryTwoMethod = EduCostStatServiceGrpc.getQueryTwoMethod) == null) {
          EduCostStatServiceGrpc.getQueryTwoMethod = getQueryTwoMethod = 
              io.grpc.MethodDescriptor.<proto.EduCostStat.QueryTwoRequest, proto.EduCostStat.QueryTwoResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.EduCostStatService", "QueryTwo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryTwoRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryTwoResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EduCostStatServiceMethodDescriptorSupplier("QueryTwo"))
                  .build();
          }
        }
     }
     return getQueryTwoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.EduCostStat.QueryThreeRequest,
      proto.EduCostStat.QueryThreeResponse> getQueryThreeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryThree",
      requestType = proto.EduCostStat.QueryThreeRequest.class,
      responseType = proto.EduCostStat.QueryThreeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.EduCostStat.QueryThreeRequest,
      proto.EduCostStat.QueryThreeResponse> getQueryThreeMethod() {
    io.grpc.MethodDescriptor<proto.EduCostStat.QueryThreeRequest, proto.EduCostStat.QueryThreeResponse> getQueryThreeMethod;
    if ((getQueryThreeMethod = EduCostStatServiceGrpc.getQueryThreeMethod) == null) {
      synchronized (EduCostStatServiceGrpc.class) {
        if ((getQueryThreeMethod = EduCostStatServiceGrpc.getQueryThreeMethod) == null) {
          EduCostStatServiceGrpc.getQueryThreeMethod = getQueryThreeMethod = 
              io.grpc.MethodDescriptor.<proto.EduCostStat.QueryThreeRequest, proto.EduCostStat.QueryThreeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.EduCostStatService", "QueryThree"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryThreeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryThreeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EduCostStatServiceMethodDescriptorSupplier("QueryThree"))
                  .build();
          }
        }
     }
     return getQueryThreeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.EduCostStat.QueryFourRequest,
      proto.EduCostStat.QueryFourResponse> getQueryFourMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryFour",
      requestType = proto.EduCostStat.QueryFourRequest.class,
      responseType = proto.EduCostStat.QueryFourResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.EduCostStat.QueryFourRequest,
      proto.EduCostStat.QueryFourResponse> getQueryFourMethod() {
    io.grpc.MethodDescriptor<proto.EduCostStat.QueryFourRequest, proto.EduCostStat.QueryFourResponse> getQueryFourMethod;
    if ((getQueryFourMethod = EduCostStatServiceGrpc.getQueryFourMethod) == null) {
      synchronized (EduCostStatServiceGrpc.class) {
        if ((getQueryFourMethod = EduCostStatServiceGrpc.getQueryFourMethod) == null) {
          EduCostStatServiceGrpc.getQueryFourMethod = getQueryFourMethod = 
              io.grpc.MethodDescriptor.<proto.EduCostStat.QueryFourRequest, proto.EduCostStat.QueryFourResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.EduCostStatService", "QueryFour"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryFourRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryFourResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EduCostStatServiceMethodDescriptorSupplier("QueryFour"))
                  .build();
          }
        }
     }
     return getQueryFourMethod;
  }

  private static volatile io.grpc.MethodDescriptor<proto.EduCostStat.QueryFiveRequest,
      proto.EduCostStat.QueryFiveResponse> getQueryFiveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "QueryFive",
      requestType = proto.EduCostStat.QueryFiveRequest.class,
      responseType = proto.EduCostStat.QueryFiveResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<proto.EduCostStat.QueryFiveRequest,
      proto.EduCostStat.QueryFiveResponse> getQueryFiveMethod() {
    io.grpc.MethodDescriptor<proto.EduCostStat.QueryFiveRequest, proto.EduCostStat.QueryFiveResponse> getQueryFiveMethod;
    if ((getQueryFiveMethod = EduCostStatServiceGrpc.getQueryFiveMethod) == null) {
      synchronized (EduCostStatServiceGrpc.class) {
        if ((getQueryFiveMethod = EduCostStatServiceGrpc.getQueryFiveMethod) == null) {
          EduCostStatServiceGrpc.getQueryFiveMethod = getQueryFiveMethod = 
              io.grpc.MethodDescriptor.<proto.EduCostStat.QueryFiveRequest, proto.EduCostStat.QueryFiveResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "proto.EduCostStatService", "QueryFive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryFiveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  proto.EduCostStat.QueryFiveResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new EduCostStatServiceMethodDescriptorSupplier("QueryFive"))
                  .build();
          }
        }
     }
     return getQueryFiveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static EduCostStatServiceStub newStub(io.grpc.Channel channel) {
    return new EduCostStatServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static EduCostStatServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new EduCostStatServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static EduCostStatServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new EduCostStatServiceFutureStub(channel);
  }

  /**
   * <pre>
   * EduCostStatService provides methods for querying education cost statistics.
   * </pre>
   */
  public static abstract class EduCostStatServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Define the QueryOne method request and response messages
     * </pre>
     */
    public void queryOne(proto.EduCostStat.QueryOneRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryOneResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryOneMethod(), responseObserver);
    }

    /**
     * <pre>
     * Define the QueryTwo method request and response messages
     * </pre>
     */
    public void queryTwo(proto.EduCostStat.QueryTwoRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryTwoResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryTwoMethod(), responseObserver);
    }

    /**
     * <pre>
     * Define the QueryThree method request and response messages
     * </pre>
     */
    public void queryThree(proto.EduCostStat.QueryThreeRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryThreeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryThreeMethod(), responseObserver);
    }

    /**
     * <pre>
     * Define the QueryFour method request and response messages
     * </pre>
     */
    public void queryFour(proto.EduCostStat.QueryFourRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryFourResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryFourMethod(), responseObserver);
    }

    /**
     * <pre>
     * Define the QueryFive method request and response messages
     * </pre>
     */
    public void queryFive(proto.EduCostStat.QueryFiveRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryFiveResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getQueryFiveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getQueryOneMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.EduCostStat.QueryOneRequest,
                proto.EduCostStat.QueryOneResponse>(
                  this, METHODID_QUERY_ONE)))
          .addMethod(
            getQueryTwoMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.EduCostStat.QueryTwoRequest,
                proto.EduCostStat.QueryTwoResponse>(
                  this, METHODID_QUERY_TWO)))
          .addMethod(
            getQueryThreeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.EduCostStat.QueryThreeRequest,
                proto.EduCostStat.QueryThreeResponse>(
                  this, METHODID_QUERY_THREE)))
          .addMethod(
            getQueryFourMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.EduCostStat.QueryFourRequest,
                proto.EduCostStat.QueryFourResponse>(
                  this, METHODID_QUERY_FOUR)))
          .addMethod(
            getQueryFiveMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                proto.EduCostStat.QueryFiveRequest,
                proto.EduCostStat.QueryFiveResponse>(
                  this, METHODID_QUERY_FIVE)))
          .build();
    }
  }

  /**
   * <pre>
   * EduCostStatService provides methods for querying education cost statistics.
   * </pre>
   */
  public static final class EduCostStatServiceStub extends io.grpc.stub.AbstractStub<EduCostStatServiceStub> {
    private EduCostStatServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EduCostStatServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EduCostStatServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EduCostStatServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Define the QueryOne method request and response messages
     * </pre>
     */
    public void queryOne(proto.EduCostStat.QueryOneRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryOneResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getQueryOneMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Define the QueryTwo method request and response messages
     * </pre>
     */
    public void queryTwo(proto.EduCostStat.QueryTwoRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryTwoResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getQueryTwoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Define the QueryThree method request and response messages
     * </pre>
     */
    public void queryThree(proto.EduCostStat.QueryThreeRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryThreeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getQueryThreeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Define the QueryFour method request and response messages
     * </pre>
     */
    public void queryFour(proto.EduCostStat.QueryFourRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryFourResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getQueryFourMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Define the QueryFive method request and response messages
     * </pre>
     */
    public void queryFive(proto.EduCostStat.QueryFiveRequest request,
        io.grpc.stub.StreamObserver<proto.EduCostStat.QueryFiveResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getQueryFiveMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * EduCostStatService provides methods for querying education cost statistics.
   * </pre>
   */
  public static final class EduCostStatServiceBlockingStub extends io.grpc.stub.AbstractStub<EduCostStatServiceBlockingStub> {
    private EduCostStatServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EduCostStatServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EduCostStatServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EduCostStatServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Define the QueryOne method request and response messages
     * </pre>
     */
    public proto.EduCostStat.QueryOneResponse queryOne(proto.EduCostStat.QueryOneRequest request) {
      return blockingUnaryCall(
          getChannel(), getQueryOneMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Define the QueryTwo method request and response messages
     * </pre>
     */
    public proto.EduCostStat.QueryTwoResponse queryTwo(proto.EduCostStat.QueryTwoRequest request) {
      return blockingUnaryCall(
          getChannel(), getQueryTwoMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Define the QueryThree method request and response messages
     * </pre>
     */
    public proto.EduCostStat.QueryThreeResponse queryThree(proto.EduCostStat.QueryThreeRequest request) {
      return blockingUnaryCall(
          getChannel(), getQueryThreeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Define the QueryFour method request and response messages
     * </pre>
     */
    public proto.EduCostStat.QueryFourResponse queryFour(proto.EduCostStat.QueryFourRequest request) {
      return blockingUnaryCall(
          getChannel(), getQueryFourMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Define the QueryFive method request and response messages
     * </pre>
     */
    public proto.EduCostStat.QueryFiveResponse queryFive(proto.EduCostStat.QueryFiveRequest request) {
      return blockingUnaryCall(
          getChannel(), getQueryFiveMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * EduCostStatService provides methods for querying education cost statistics.
   * </pre>
   */
  public static final class EduCostStatServiceFutureStub extends io.grpc.stub.AbstractStub<EduCostStatServiceFutureStub> {
    private EduCostStatServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private EduCostStatServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected EduCostStatServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new EduCostStatServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Define the QueryOne method request and response messages
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.EduCostStat.QueryOneResponse> queryOne(
        proto.EduCostStat.QueryOneRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getQueryOneMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Define the QueryTwo method request and response messages
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.EduCostStat.QueryTwoResponse> queryTwo(
        proto.EduCostStat.QueryTwoRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getQueryTwoMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Define the QueryThree method request and response messages
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.EduCostStat.QueryThreeResponse> queryThree(
        proto.EduCostStat.QueryThreeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getQueryThreeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Define the QueryFour method request and response messages
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.EduCostStat.QueryFourResponse> queryFour(
        proto.EduCostStat.QueryFourRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getQueryFourMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Define the QueryFive method request and response messages
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<proto.EduCostStat.QueryFiveResponse> queryFive(
        proto.EduCostStat.QueryFiveRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getQueryFiveMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_QUERY_ONE = 0;
  private static final int METHODID_QUERY_TWO = 1;
  private static final int METHODID_QUERY_THREE = 2;
  private static final int METHODID_QUERY_FOUR = 3;
  private static final int METHODID_QUERY_FIVE = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final EduCostStatServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(EduCostStatServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_ONE:
          serviceImpl.queryOne((proto.EduCostStat.QueryOneRequest) request,
              (io.grpc.stub.StreamObserver<proto.EduCostStat.QueryOneResponse>) responseObserver);
          break;
        case METHODID_QUERY_TWO:
          serviceImpl.queryTwo((proto.EduCostStat.QueryTwoRequest) request,
              (io.grpc.stub.StreamObserver<proto.EduCostStat.QueryTwoResponse>) responseObserver);
          break;
        case METHODID_QUERY_THREE:
          serviceImpl.queryThree((proto.EduCostStat.QueryThreeRequest) request,
              (io.grpc.stub.StreamObserver<proto.EduCostStat.QueryThreeResponse>) responseObserver);
          break;
        case METHODID_QUERY_FOUR:
          serviceImpl.queryFour((proto.EduCostStat.QueryFourRequest) request,
              (io.grpc.stub.StreamObserver<proto.EduCostStat.QueryFourResponse>) responseObserver);
          break;
        case METHODID_QUERY_FIVE:
          serviceImpl.queryFive((proto.EduCostStat.QueryFiveRequest) request,
              (io.grpc.stub.StreamObserver<proto.EduCostStat.QueryFiveResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class EduCostStatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    EduCostStatServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return proto.EduCostStat.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("EduCostStatService");
    }
  }

  private static final class EduCostStatServiceFileDescriptorSupplier
      extends EduCostStatServiceBaseDescriptorSupplier {
    EduCostStatServiceFileDescriptorSupplier() {}
  }

  private static final class EduCostStatServiceMethodDescriptorSupplier
      extends EduCostStatServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    EduCostStatServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (EduCostStatServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new EduCostStatServiceFileDescriptorSupplier())
              .addMethod(getQueryOneMethod())
              .addMethod(getQueryTwoMethod())
              .addMethod(getQueryThreeMethod())
              .addMethod(getQueryFourMethod())
              .addMethod(getQueryFiveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
