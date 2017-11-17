package Manager;

import java.time.LocalTime;

public class Service {
    private final String type;
    private final String ip;
    private final String port;
    private LocalTime lastRegistration;
    
    public Service(String type, String ip, String port){
        this.type= type;
        this.ip = ip;
        this.port = port;
        this.lastRegistration = LocalTime.now();
    }

    public String getType() { return this.type; }

    public String getIP() { return this.ip; }

    public String getPort() { return this.port; }

    public LocalTime getLastRegistration() { return this.lastRegistration; }

    public void setLastRegistration(LocalTime time) {
        this.lastRegistration = time;
    }
}
