package Services;

import Connection.ConnectionHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author wanderson
 */
public class AverageRunner {
    private final ServerSocket server;
    private final Socket host;
    
    public AverageRunner(ServerSocket server, Socket host) {
        this.server = server;
        this.host = host;
    }
    
    public void run() {
        AverageRegister w = new AverageRegister(3 * 60 * 1000,
                this.server.getLocalPort(), "localhost", 3000);
        w.start();
        
        do {
            try {
                System.out.println("Average Server running on " + this.server.getLocalPort());
                
                Socket conn = this.server.accept();
                System.out.println("Connection received...");
                
                ConnectionHandler ch = new ConnectionHandler(conn, "average");
                ch.start();
            } catch(IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while(true);
    }
}
