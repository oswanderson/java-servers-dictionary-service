package Connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {
    private Socket socket;
    
    public Connection(Socket socket){
        this.socket = socket;
    }
    
    public String read(){
        String content = "";
        try {
            int i;
            InputStream in = this.socket.getInputStream();
            byte[] buffer = new byte[512];
            int n = in.read(buffer);
            if(n != -1) {
                content += new String(buffer, 0, n);
            }
        }catch (IOException e) {
            System.out.println(e.toString());
            return "";
        }
        return content;
    }
    
    public boolean write(String content){
        OutputStream out;
        try {
            out = this.socket.getOutputStream();
            out.write(content.getBytes());
        } catch(IOException e) {
            System.out.println("ERROR WHEN WRITING TO CONN: " + e.toString());
        }
        return true;
    }
    
    public Socket getSocket(){
        return this.socket;
    }
    
    public void setSocket(Socket socket){
        this.socket = socket;
    }
    
    public boolean close() {
        try {
            this.socket.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
