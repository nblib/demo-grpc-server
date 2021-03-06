package demo.hewe;

import demo.hewe.Services.DemoService;
import demo.hewe.Services.HelloService;
import demo.hewe.Services.JiheServiceImpl;
import demo.hewe.Services.SampleService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    private int port = 50051;
    private Server server;

    private void start() throws IOException {
        //添加需要运行监听供客户端调用的服务
        server = ServerBuilder.forPort(port)
                .addService(new HelloService())
                .addService(new DemoService())
                .addService(new SampleService())
                .addService(new JiheServiceImpl())
                .build()
                .start();


        System.out.println("service start...");

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                App.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    // block 一直到退出程序
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        App app = new App();
        app.start();
        app.blockUntilShutdown();

    }
}
