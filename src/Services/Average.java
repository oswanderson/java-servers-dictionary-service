package Services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author wanderson
 */
public class Average {
    public static void main(String[] args) {
        try {
            // Local
            int port = 3001;
            ServerSocket server = new ServerSocket(port);
            
            // Host
            Socket host = new Socket("localhost", 3001);
            
            AverageRunner runner = new AverageRunner(server, host);
            runner.run();
            
        } catch(IOException e) {
            System.out.println("ERROR:" + e.getMessage());
            System.exit(1);
        }
    }
}
