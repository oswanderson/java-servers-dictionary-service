package Connection;

import Server.Protocol;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
    private final Socket socket;
    
    public ConnectionHandler(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run(){
        Connection conn = new Connection(this.socket);
        String requestContent = conn.read();
        Protocol p = new Protocol(requestContent);
        
        if (p.isValid()){
            conn.write(p.process());
        } else {
            conn.write(p.getErrorMessage());
        }
    }
}
