package Manager;

import java.time.LocalTime;
import java.util.Date;

public class Service {
    private String type;
    private String ip;
    private String port;
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
