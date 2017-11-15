package Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {
    private Socket socket;
    
    public Connection(){}
    
    public Connection(Socket socket){
        this.socket = socket;
    }
    
    public String read(){
        String content = "";
        try{
            InputStream in = this.socket.getInputStream();
            byte[] buffer = new byte[206];
            while(in.read(buffer) != -1){
                content += new String(buffer);
            }
        }catch (IOException e) {
            System.out.println(e.toString());
        }
        return content;
    }
    
    public boolean write(String content){
        try {
            OutputStream out = this.socket.getOutputStream();
            out.write(content.getBytes());
            this.socket.close();
        } catch(IOException e) {
            System.out.println(e.toString());
        }
        return true;
    }
    
    public Socket getSocket(){
        return this.socket;
    }
    
    public void setSocket(Socket socket){
        this.socket = socket;
    }
}
