package demo.hewe.Services;

import demo.hewe.io.jihe.Jihe;
import demo.hewe.io.jihe.JiheServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.List;

/**
 * @year 2018
 * @project demo-grpc-server
 * @description:<p>使用list和map的实例</p>
 **/
public class JiheServiceImpl extends JiheServiceGrpc.JiheServiceImplBase {

    @Override
    public void transferToMap(Jihe.ListCar request, StreamObserver<Jihe.MapCar> responseObserver) {
        //数量
        System.out.println(String.format("count:    %d", request.getCarCount()));
        //获取指定位置的Car
        if (request.getCarCount() > 2) {
            Jihe.Car car = request.getCar(1);
            System.out.println(String.format("index 1: {id: %d,name: %s,color: %s}", car.getId(), car.getName(), car.getColor()));
        }
        //获取list
        List<Jihe.Car> carList = request.getCarList();
        //新建返回的map
        Jihe.MapCar.Builder mapBuilder = Jihe.MapCar.newBuilder();
        //转换成map,以id为key,car为value
        for (Jihe.Car car : carList) {
            mapBuilder.putCars(car.getId(), car);
        }
        //完成
        responseObserver.onNext(mapBuilder.build());
        responseObserver.onCompleted();
    }
}
