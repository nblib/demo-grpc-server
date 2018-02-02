package demo.hewe.Services;

import demo.hewe.io.common.CommonProto;
import demo.hewe.io.sample.PositionGrpc;
import demo.hewe.io.sample.SampleProto;
import io.grpc.stub.StreamObserver;

/**
 * @year 2018
 * @project demo-grpc-server
 * @description:<p>流测试demo</p>
 **/
public class SampleService extends PositionGrpc.PositionImplBase {
    @Override
    public StreamObserver<SampleProto.Location> postLocation(StreamObserver<CommonProto.Empty> responseObserver) {
        return new StreamObserver<SampleProto.Location>() {
            @Override
            public void onNext(SampleProto.Location location) {
                System.out.println(String.format("get location from client:[%s][%s]", location.getLat(), location.getLon()));
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(String.format("client post error: %s", throwable.getMessage()));
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(CommonProto.Empty.newBuilder().build());
                responseObserver.onCompleted();
            }
        };

    }

    @Override
    public void pullLocation(CommonProto.Empty request, StreamObserver<SampleProto.Location> responseObserver) {
        SampleProto.Location location = SampleProto.Location.newBuilder().setLon(10.23f).setLat(21.41f).build();
        SampleProto.Location location1 = SampleProto.Location.newBuilder().setLon(10.24f).setLat(21.41f).build();
        SampleProto.Location location2 = SampleProto.Location.newBuilder().setLon(10.25f).setLat(21.41f).build();
        SampleProto.Location location3 = SampleProto.Location.newBuilder().setLon(10.26f).setLat(21.41f).build();

        responseObserver.onNext(location);
        responseObserver.onNext(location1);
        responseObserver.onNext(location2);
        responseObserver.onNext(location3);

        responseObserver.onCompleted();

    }
}
