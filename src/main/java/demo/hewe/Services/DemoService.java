package demo.hewe.Services;

import com.google.protobuf.ProtocolStringList;
import demo.hewe.io.common.CommonProto;
import demo.hewe.io.demo.DemoServiceGrpc;
import demo.hewe.io.demo.DemooProto;
import io.grpc.stub.StreamObserver;

/**
 * @year 2018
 * @project demo-grpc-server
 * @description:<p>测试list和map类型</p>
 **/
public class DemoService extends DemoServiceGrpc.DemoServiceImplBase {
    @Override
    public void checkIfBlack(DemooProto.CheckIps request, StreamObserver<DemooProto.CheckResult> responseObserver) {
        //获取list
        ProtocolStringList ipsList = request.getIpsList();
        //构建响应消息
        DemooProto.CheckResult.Builder builder = DemooProto.CheckResult.newBuilder();
        //往map中添加数据
        for (String ip : ipsList) {
            builder.putResults(ip, true);
        }
        DemooProto.CheckResult result = builder.build();
        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    /**
     * 获取联系方式,测试Oneof
     *
     * @param request
     * @param responseObserver
     */
    @Override
    public void getContectInfo(CommonProto.Empty request, StreamObserver<DemooProto.ContectInfo> responseObserver) {
        DemooProto.ContectInfo contectInfo = DemooProto.ContectInfo.newBuilder().setTel("0357-6862849").build();
        responseObserver.onNext(contectInfo);
        responseObserver.onCompleted();
    }
}
