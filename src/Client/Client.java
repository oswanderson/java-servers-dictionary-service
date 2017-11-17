package Client;

import Connection.Connection;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {
    public static void main(String[] args) {
        int option = -1;
        
        while (option != 0){
            String input = showPanel(1, "Choose an option", getMessage("mainMenu"));
            
            try {
                option = Integer.parseInt(input);
                System.out.println("Option: " + option);
            } catch(NumberFormatException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
            
            String service;
            
            switch(option) {
                // Exit
                case 0:
                    System.out.println("Getting out...");
                    break;
                // Dictionary
                case 1:
                    System.out.println("Choose a service...");
                    service = showPanel(1, "Choose a service", getMessage("servicesOptions"));
                    
                    try {
                        option = Integer.parseInt(service);
                        dictionary(option);
                    } catch(NumberFormatException e) {
                        System.out.println("ERROR: " + e.getMessage());
                        showPanel(-1, "", "Invalid option!");
                    }
                    break;
                // Service
                case 2:
                    System.out.println("Consume service...");
                    service = showPanel(1, "Choose a service", getMessage("servicesOptions"));
                    try {
                        option = Integer.parseInt(service);
                        String ip = showPanel(1, "Service IP", "");
                        int port = Integer.parseInt(showPanel(1, "Service Port", ""));
                        consumeService(option, ip, port);
                    } catch(NumberFormatException e) {
                        showPanel(-1, "", "Error when creating request");
                        System.out.println("ERROR: " + e.getLocalizedMessage());
                    }
                    break;
                default:
                    showPanel(-1, "", "Invalid option!");
                    break;
            }
        }
        
        System.exit(0);
    }
    
    public static void dictionary(int type){
        try (Socket socket = new Socket("localhost", 3000)) {
            String body = "req:service_search\n";
            body += "type:";
            
            switch(type) {
                // Average service
                case 1:
                    body += "avg";
                    break;
                // BMI service
                case 2:
                    body += "bmi";
                    break;
                // Translation service
                case 3:
                    body += "trs";
                    break;
                default:
                    showPanel(-1, "", "Invalid option!");
                    return;
            }
            
            Connection conn = new Connection(socket);
            
            if (conn.write(body)) {
                String response = conn.read();
                showPanel(0, "Response", response);
            } else {
                showPanel(-1, "", "Error when trying to send data to server...");
            }
            
        } catch(IOException e) {
            showPanel(-1, "", "Error when trying to connect to server...");
        }
    }
    
    public static void consumeService(int type, String host, int port){
        try (Socket socket = new Socket(host, port)) {
            String body = "";
            
            switch(type) {
                // Average service
                case 1:
                    body += showPanel(1, "Average - Type your numbers", getMessage("avgService"));
                    break;
                // BMI service
                case 2:
                    body += "bmi";
                    break;
                // Translation service
                case 3:
                    body += "trs";
                    break;
                default:
                    showPanel(-1, "", "Invalid option!");
                    return;
            }
            
            Connection conn = new Connection(socket);
            
            if (conn.write(body)) {
                String response = conn.read();
                showPanel(0, "Service response", response);
                conn.close();
            } else {
                showPanel(-1, "", "Error when trying to send data to server...");
                conn.close();
            }
            
        } catch(IOException e) {
            showPanel(-1, "", "Error when trying to connect to server...");
        }
    }
    
    public static String getMessage(String type) {
        switch(type) {
            case "mainMenu":
                return "1 - Dictionary\n2 - Service\n0 - Exit";
            case "servicesOptions":
                return "1 - Average\n2 - BMI\n3 - Translation";
            case "avgInput":
                return "Use ';' as delimiter";
            case "bmiInput":
                return "";
            case "trsInput":
                return "";
            case "ipInput":
                return "Type the service ip";
            case "portInput":
                return "Type the service port";
            default:
                return "";
        }
    }
    
    public static String showPanel(int type, String title, String message) {
        switch(type) {
            // Error
            case -1:
                JOptionPane.showMessageDialog(null, message,
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                return "";
            // Show message
            case 0:
                JOptionPane.showMessageDialog(null, message,
                        title, JOptionPane.PLAIN_MESSAGE);
                return "";
            // Show input
            case 1:
                String input = JOptionPane.showInputDialog(null, message,
                    title, JOptionPane.INFORMATION_MESSAGE);
                return input;
            default:
                return "";
        }
    }
}
