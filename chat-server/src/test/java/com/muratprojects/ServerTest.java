package com.muratprojects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    private Server server;
    private Thread thread;
    private final static Logger logger = LoggerFactory.getLogger(ServerTest.class);

    @Before
    public void setUp() throws InterruptedException {
        server = new Server();
        logger.info("launched: {} , {}", server.getClass() , server.hashCode());
        thread = new Thread(server);
        thread.start();
        logger.info("running finished: {} , {}", server.getClass() , server.hashCode());
    }
    @After
    public void shutDown() throws Exception{
        server.shutDown();
    }
    @Test
    public void testConnection() throws Exception{
        TimeUnit.SECONDS.sleep(1);

        try (Socket client = new Socket("127.0.0.1", 9999);
             PrintWriter out = new PrintWriter(client.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                ){
            String response = in.readLine();
            assertEquals("Please enter nickname: ",response);
            out.println("Murat");
            response = in.readLine();
            assertEquals("Murat joined the chat!", response);
        }
    }
    @Test
    public void testBroadcast() throws Exception{
        // TODO: implement testing
    }
}
