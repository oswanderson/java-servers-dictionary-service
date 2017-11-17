package Manager;

public class Protocol {
    private String service;
    private String type;
    private String ip;
    private String port;
    private final String content;
    private boolean valid;
    private String errorMessage;
    private static final String SERVICE_REGISTRATION = "service_registration";
    private static final String SERVICE_SEARCH = "service_search";

    public Protocol(String content) {
        this.content = content;
    }

    public void process() {
        String[] request;
        try {
            request = this.content.split("\n");
            this.service = request[0].split(":")[1];
            this.type = request[1].split(":")[1];
        } catch (Exception e) {
            this.valid = false;
            this.errorMessage = "Invalid request.";
            return;
        }
        
        this.valid = true;
        
        Manager m = Manager.getInstance();
        Service s;
        
        switch (this.service) {
            case SERVICE_REGISTRATION:
                System.out.println("Service registration...");
                if (request.length < 4 || !validType(this.type)) {
                    this.valid = false;
                    this.errorMessage = "Invalid request or service type.";
                    break;
                }
                
                try {
                    this.ip = request[2].split(":")[1];
                    this.port = request[3].split(":")[1];
                } catch(Exception e) {
                    this.valid = false;
                    this.errorMessage = "Invalid request.";
                    return;
                }
                
                s = new Service(this.type, this.ip, this.port);
                m.addService(s.getType(), s);
                System.out.println("Service of type " + this.type + " registered...");
                
                break;
            case SERVICE_SEARCH:
                System.out.println("Service search...");
                if (request.length > 2 || !validType(this.type)) {
                    this.valid = false;
                    this.errorMessage = "Invalid request or service type.";
                    break;
                }
                
                s = m.getService(this.type);
                if (s == null) {
                    this.ip = "";
                    this.port = "";
                    this.errorMessage = "No service of type: " + this.type;
                    break;
                }
                
                this.ip = s.getIP();
                this.port = s.getPort();
                break;
            default:
                this.valid = false;
                this.errorMessage = "Invalid service.";
                break;
        }
    }
    
    public boolean validType(String type) {
        String[] validTypes = {"avg", "bmi", "trs"};
        
        boolean v = false;
        for (String t : validTypes) {            
            if (t.equals(type)) {
                v = true;
                break;
            }
        }
        return v;
    }

    public boolean isValid() {
        return this.valid;
    }
    
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
