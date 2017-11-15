package Server;

import Manager.Dictionary;
import Manager.Service;

public class Protocol {
    private String TYPE;
    private String IP;
    private String PORT;
    private String CONTENT;
    private boolean VALID;
    private String ErrorMessage;
    private static final String SERVICE_REGISTRATION = "service_registration";
    private static final String SERVICE_SEARCH = "service_search";
    
    public Protocol(String content){
        this.CONTENT = content;
    }
    
    public String process(){
        String[] c = this.CONTENT.split("\n");
        if (c.length < 2) {
            return "";
        } else {
            this.TYPE = c[0];
        }
        
        switch (this.TYPE){
            case SERVICE_REGISTRATION:
                
                break;
            case SERVICE_SEARCH:
                Dictionary d = new Dictionary();
                break;
            default:
                this.VALID = false;
                break;
        }
        return "";
    }
    
    public boolean isValid(){
        return this.VALID;
    }
    
    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String ErrorMessage) {
        this.ErrorMessage = ErrorMessage;
    }
}
