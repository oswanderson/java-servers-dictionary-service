package Connection;

import Manager.Protocol;
import java.net.Socket;

public class ConnectionHandler extends Thread{
    private Socket socket;
    private String type;
    
    public ConnectionHandler(Socket socket, String type){
        this.socket = socket;
        this.type = type;
    }
    
    @Override
    public void run(){
        switch(this.type) {
            case "server":
                server();
                break;
            case "average":
                average();
                break;
            case "bmi":
                bmi();
                break;
            case "translation":
                translation();
                break;
        }
    }
    
    private void server() {
        Connection conn = new Connection(this.socket);
        String requestContent = conn.read();
        Protocol p = new Protocol(requestContent);
        p.process();
        
        if (p.isValid()){
            String response = "";
            switch (p.getService()) {
                case "service_registration":
                    response += "Service registered.";
                    break;
                case "service_search":
                    response += "Service:" + p.getType() + "\n";
                    response += "IP:" + p.getIp() + "\n";
                    response += "PORT:" + p.getPort();
                    break;
            }
            
            conn.write(response);
        } else {
            conn.write(p.getErrorMessage());
        }
        conn.close();
    }
    
    private void average() {
        Connection conn = new Connection(this.socket);
        String content = conn.read();
        System.out.println("Content from connection: " + content);
        
        try {
            String[] numberList = content.split(";");
            if(numberList.length < 2) {
                conn.write("The amount of numbers must be at least 2...");
                conn.close();
                return;
            }
            
            Double[] numbers = new Double[numberList.length];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Double.parseDouble(numberList[i]);
            }
            
            conn.write("" + calcAverage(numbers));
        } catch(NumberFormatException e) {
            conn.write("Invalid format of numbers list.");
        }
        conn.close();
    }
    
    private Double calcAverage(Double[] numbers){
        Double sum = 0.0;
        for (Double n : numbers) {
            sum += n;
        }
        
        return sum / numbers.length;
    }
    
    private void bmi() {
        
    }
    
    private void translation() {
        
    }
    
}
