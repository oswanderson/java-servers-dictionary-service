
package Services;

import Connection.Connection;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author wanderson
 */
public class AverageRegister extends Thread {
    private final long timer;
    private final int localPort;
    private final String host;
    private final int port;

    public AverageRegister(long timer, int localPort, String host, int port) {
        this.timer = timer;
        this.localPort = localPort;
        this.host = host;
        this.port = port;
    }
    
    @Override
    public void run() {
        try {
            String request = "req:service_registration\n";
            request += "type:avg\n";
            request += "ip:" + InetAddress.getLocalHost().getHostAddress() + "\n";
            request += "port:" + this.localPort;
            
            do {
                System.out.println("Sending registration for Average Server...");
                Socket socket = new Socket(this.host, this.port);
                Connection conn = new Connection(socket);
                conn.write(request);
                
                System.out.println("=== Response from Server:");
                System.out.println(conn.read());
                conn.close();
                
                Thread.sleep(this.timer);
            } while(true);
            
        } catch(IOException | InterruptedException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
    }
    
}
