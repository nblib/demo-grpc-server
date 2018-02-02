package demo.hewe.Services;

import demo.hewe.io.hello.HelloProto;
import demo.hewe.io.hello.HelloSerivceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;

/**
 * @year 2018
 * @project demo-grpc-server
 * @description:<p>helloProto的实现类,通过继承类,复写方法</p>
 **/
public class HelloService extends HelloSerivceGrpc.HelloSerivceImplBase {
    @Override
    public void tickInfo(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloReply> responseObserver) {
        String info = String.format("name=%s,age=%d,isAdult=%b",//
                request.getName(), request.getAge(), request.getIsAdult());
        //构建返回的message
        HelloProto.HelloReply reply = HelloProto.HelloReply.newBuilder()//
                .setInfo(info)//
                .setReceiveTime(LocalDateTime.now().toString())//
                .build();
        //传递返回的message
        responseObserver.onNext(reply);
        //标识完成处理
        responseObserver.onError(Status.fromCode(Status.Code.INTERNAL).asException());
        responseObserver.onCompleted();
    }
}
