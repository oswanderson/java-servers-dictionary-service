package Server;

import Connection.*;
import Manager.Manager;
import java.io.IOException;
import java.net.*;

public class DictionaryServices {
    
    static ServerSocket server;
    
    public static void run(int port){
        try{
            server = new ServerSocket(port);
            System.out.println("Server running at localhost:" + port + "...");
        }catch (IOException e){
            System.out.println(e.toString());
        }
        
        Manager m = Manager.getInstance();
        Watcher w = new Watcher(1 * 20 * 1000, 1);
        w.start();
        
        while (true){
            try{
                ConnectionHandler ch = new ConnectionHandler(server.accept(), "server");
                ch.start();
            }catch(IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
