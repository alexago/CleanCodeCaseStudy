package cleancoderscom.socketserver;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.*;

@RunWith(HierarchicalContextRunner.class)
public class SocketServerTest {
    private SocketServer server;
    private final int PORT = 8042;

    public class WithClosingSocketService {
        private ClosingSocketService service;
        @Before
        public void setUp() {
            service = new ClosingSocketService();
            server = new SocketServer(PORT, service);
        }

        @After
        public void tearDown() throws Exception {
            if (server.isRunning()) {
                server.stop();
            }
        }

        @Test
        public void instantiate() {
            assertEquals(PORT, server.getPort());
            assertEquals(service, server.getService());
        }

        @Test
        public void canStartAndStopServer() throws Exception {
            server.start();
            assertTrue(server.isRunning());
            Socket socket = new Socket("localhost", PORT);

            server.stop();
            assertFalse(server.isRunning());
        }

        @Test
        public void acceptAnIncomingConnection() throws Exception {
            server.start();
            Socket socket = new Socket("localhost", PORT);
            synchronized (service) {
                service.wait();
            }
            server.stop();

            assertEquals(1, service.connections);
        }

        @Test
        public void acceptMultipleIncomingConnections() throws Exception {
            server.start();
            new Socket("localhost", PORT);
            synchronized (service) {
                service.wait();
            }
            new Socket("localhost", PORT);
            synchronized (service) {
                service.wait();
            }
            new Socket("localhost", PORT);
            synchronized (service) {
                service.wait();
            }
            server.stop();

            assertEquals(3, service.connections);
        }
    }

    public class WithReadingSocketService {
        private ReadingSocketService readingService;

        @Before
        public void setup() throws Exception {
            readingService = new ReadingSocketService();
            server = new SocketServer(PORT, readingService);
        }

        @Test
        public void canSendAndReceiveData() throws Exception {
            server.start();
            Socket socket = new Socket("localhost", PORT);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello\n".getBytes());
            synchronized (readingService) {
                readingService.wait();
            }
            server.stop();
            assertEquals("hello", readingService.message);
        }
    }

    public class WithEchoSocketService {
        private EchoSocketService echoService;

        @Before
        public void setup() throws Exception {
            echoService = new EchoSocketService();
            server = new SocketServer(PORT, echoService);
        }

        @Test
        public void canSendAndReceiveData() throws Exception {
//            server.start();
//            Socket socket = new Socket("localhost", PORT);
//            OutputStream outputStream = socket.getOutputStream();
//            outputStream.write("hello\n".getBytes());
//            synchronized (readingService) {
//                readingService.wait();
//            }
//            server.stop();
//            assertEquals("hello", readingService.message);
        }
    }

    public static class ClosingSocketService extends TestSocketService {
        public int connections;

        @Override
        protected void doServe(Socket socket) {
            connections++;
        }
    }

    public static class ReadingSocketService extends TestSocketService {
        public String message;

        @Override
        protected void doServe(Socket socket) throws IOException {
            InputStream is = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bis = new BufferedReader(reader);
            message = bis.readLine();
        }
    }

    public static class EchoSocketService extends TestSocketService {
        @Override
        protected void doServe(Socket socket) {

        }
    }
}
