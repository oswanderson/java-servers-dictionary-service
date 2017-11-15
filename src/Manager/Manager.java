package Manager;

import java.time.*;
import java.util.ArrayList;
import java.util.Iterator;

// Manager is a singleton class and its function is manage the list of services
public class Manager {
    private static Manager singleton = new Manager();
    private static ArrayList<Service> imc = new ArayList<>();
    private static ArrayList<Service> average = new ArayList<>();
    private static ArrayList<Service> translate = new ArrayList<>();

    private static int last_imc = 0;
    private static int last_average = 0;
    private static int last_translate = 0;
    
    private Manager() { }
    
    public static synchronized Manager getInstance() {
        return singleton;
    }
    
    public static synchronized void addService(String type, Service service) {
        switch (type) {
            case "imc":
                imc.add(service);
                break;
            case "avg":
                average.add(service);
                break;
            case "trs":
                translate.add(service);
                break;
        }
    }

    public static synchronized Service getService(String type) {
        int index = choose(type);

        if (index == -1) {
            return null;
        } else {
            switch (type) {
                case "imc":
                    return imc.get(index);
                case "avg":
                    return average.get(index);
                case "trs":
                    return translate.get(index);
            }
        }
    }

    private static int choose(string type) {
        switch (type) {
            case "imc":
                if (imc.isEmpty()) {
                    return -1;
                } else if (imc.size() == last_imc) {
                    return 0;
                } else {
                    last_imc++;
                    return last_imc;
                }
            case "avg":
                if (average.isEmpty()) {
                    return -1;
                } else if (average.size() == last_average) {
                    return 0;
                } else {
                    last_average++;
                    return last_average;
                }
            case "trs":
                if (translate.isEmpty()) {
                    return -1;
                } else if (translate.size() == last_translate) {
                    return 0;
                } else {
                    last_translate++;
                    return last_translate;
                }
        }
    }

    public static synchronized void removeOld(int minutes) {
        long maxMinutes = 3;
        LocalTime now = LocalTime.now();
        ArrayList<Service> toRemove = new ArrayList<>();

        for (Service s : imc) {
            if (isOld(s.getLastRegistration(), now, maxMinutes)) {
                toRemove.add(s);
            }
        }
        imc.removeAll(toRemove);

        toRemove.clear();
        for (Service s : average) {
            if (isOld(s.getLastRegistration(), now, maxMinutes)) {
                toRemove.add(s);
            }
        }
        average.removeAll(toRemove);

        toRemove.clear();
        for (Service s : translate) {
            if (isOld(s.getLastRegistration(), now, maxMinutes)) {
                toRemove.add(s);
            }
        }
        translate.removeAll(toRemove);
    }

    private static boolean isOld(LocalTime t1, LocalTime t2, long maxMinutes) {
        Duration duration = Duration(t1, t2);
        long minutes = duration.toMinutes();

        if (minutes > maxMinutes) {
            return true;
        }
        return false;
    }
}
