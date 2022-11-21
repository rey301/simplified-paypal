package com.webapps2022.ejb;

import com.webapps2022.thrift.TimestampService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

// Runs the server at port 10001 to handle clients requesting timestamps
public class TimestampServer {

    public static TimestampHandler handler;
    public static TimestampService.Processor processor;
    public static TServerTransport serverTransport;
    public static TServer server;

    public static void main(String[] args) {
        try {
            handler = new TimestampHandler();
            processor = new TimestampService.Processor(handler);

            Runnable simple = new Runnable() {
                @Override
                public void run() {
                    simple(processor);
                }
            };

            new Thread(simple).start();
            System.in.read();
            server.stop();
            
        } catch (Exception x) {
            System.err.println(x);
        }
    }

    public static void simple(TimestampService.Processor processor) {
        try {
            serverTransport = new TServerSocket(10001);
            server = new TSimpleServer(new Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server in Thread " + Thread.currentThread().getId());
            server.serve();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

}
