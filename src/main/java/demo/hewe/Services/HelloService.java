package demo.hewe.Services;

import demo.hewe.io.hello.HelloProto;
import demo.hewe.io.hello.HelloSerivceGrpc;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;

/**
 * @year 2018
 * @project demo-grpc-server
 * @description:<p>helloProto的实现类,通过继承类,复写方法</p>
 **/
public class HelloService extends HelloSerivceGrpc.HelloSerivceImplBase {
    /**
     * 需要供客户端调用的方法实现
     *
     * @param request          客户端传来的Message,通过这个对象取出参数进行处理
     * @param responseObserver 服务端响应客户端的处理对象,用于返回Message
     */
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
        //如果服务端遇到内部错误,或者客户端参数用错误等等,使用这个方法结束处理,以后的方法不会被执行,此次请求也会断开.
        //一般在try/catch中的catch中调用,类似java中抛出了一个异常
        //responseObserver.onError(Status.fromCode(Status.Code.INTERNAL).asException());
        //标识完成处理,这是服务端会中断此次请求的返回流,标识完成
        responseObserver.onCompleted();
    }
}
