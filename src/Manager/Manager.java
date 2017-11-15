package Manager;

public class Manager {
    private static Manager singleton = new Manager();
    
    private Manager() { }
    
    public static Manager getInstance() {
        return singleton;
    }
    
    protected static void demoMethod() {
        System.out.println("demoMethod for singleton");
    }
}
