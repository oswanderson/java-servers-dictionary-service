
package Test;

import Connection.Connection;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author wanderson
 */
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 3000);
            Connection conn =  new Connection(socket);
            conn.write("caralho");
            System.out.println("Response: " + conn.read());
        } catch (IOException ex) {
            System.out.println("Failed when creating a socket...");
        }
    }
    
}
