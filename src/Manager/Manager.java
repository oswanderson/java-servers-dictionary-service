package Manager;

import java.time.*;
import java.util.ArrayList;

// Manager is a singleton class and its function is manage the services
public class Manager {
    private static final Manager SINGLETON = new Manager();
    private static ArrayList<Service> bmi = new ArrayList<>();
    private static ArrayList<Service> average = new ArrayList<>();
    private static ArrayList<Service> translation = new ArrayList<>();

    private static int last_bmi = 0;
    private static int last_average = 0;
    private static int last_translation = 0;
    
    private Manager() { }
    
    public static synchronized Manager getInstance() {
        return SINGLETON;
    }
    
    public synchronized void addService(String type, Service s) {
        switch (type) {
            case "bmi":
                verifyBMIService(s);
                break;
            case "avg":
                verifyAVGService(s);
                break;
            case "trs":
                verifyTRSService(s);
                break;
        }
    }

    public synchronized Service getService(String type) {
        int index = choose(type);

        if (index == -1) {
            return null;
        } else {
            switch (type) {
                case "bmi":
                    return bmi.get(index);
                case "avg":
                    return average.get(index);
                case "trs":
                    return translation.get(index);
            }
        }
        
        return null;
    }

    private synchronized int choose(String type) {
        switch (type) {
            case "bmi":
                if (bmi.isEmpty()) {
                    last_bmi = 0;
                    return -1;
                } else if (bmi.size() == last_bmi) {
                    last_bmi = 0;
                    return 0;
                } else {
                    last_bmi++;
                    return last_bmi;
                }
            case "avg":
                if (average.isEmpty()) {
                    last_average = 0;
                    return -1;
                } else if (average.size() - 1 == last_average) {
                    last_average = 0;
                    return 0;
                } else {
                    last_average++;
                    return last_average;
                }
            case "trs":
                if (translation.isEmpty()) {
                    last_translation = 0;
                    return -1;
                } else if (translation.size() == last_translation) {
                    last_translation = 0;
                    return 0;
                } else {
                    last_translation++;
                    return last_translation;
                }
            default:
                return -1;
        }
    }

    private boolean isOld(LocalTime t1, LocalTime t2, long maxMinutes) {
        Duration duration = Duration.between(t1, t2);
        long minutes = duration.toMinutes();

        return minutes > maxMinutes;
    }
    
    public synchronized void removeOld(int minutes) {
        long maxMinutes = minutes;
        LocalTime now = LocalTime.now();
        ArrayList<Service> toRemove = new ArrayList<>();

        System.out.println("BMI: " + bmi.size());
        for (Service s : bmi) {
            if (isOld(s.getLastRegistration(), now, maxMinutes)) {
                toRemove.add(s);
            }
        }
        bmi.removeAll(toRemove);
        System.out.println("BMI: " + bmi.size());

        System.out.println("AVG: " + average.size());
        toRemove.clear();
        for (Service s : average) {
            if (isOld(s.getLastRegistration(), now, maxMinutes)) {
                toRemove.add(s);
            }
        }
        average.removeAll(toRemove);
        System.out.println("AVG: " + average.size());

        System.out.println("TRS: " + translation.size());
        toRemove.clear();
        for (Service s : translation) {
            if (isOld(s.getLastRegistration(), now, maxMinutes)) {
                toRemove.add(s);
            }
        }
        translation.removeAll(toRemove);
        System.out.println("TRS: " + translation.size());
    }
    
    private synchronized void verifyBMIService(Service s) {
        boolean exist = false;
        
        for (int i = 0; i < bmi.size(); i++) {
            if (bmi.get(i).getIP().equals(s.getIP())
                    && bmi.get(i).getPort().equals(s.getPort())) {
                bmi.get(i).setLastRegistration(LocalTime.now());
                exist = true;
            }
        }
        
        if (!exist) {
            bmi.add(s);
        }
    }
    
    private synchronized void verifyAVGService(Service s) {
        boolean exist = false;
        
        for (int i = 0; i < average.size(); i++) {
            if (average.get(i).getIP().equals(s.getIP())
                    && average.get(i).getPort().equals(s.getPort())) {
                average.get(i).setLastRegistration(LocalTime.now());
                exist = true;
            }
        }
        
        if (!exist) {
            average.add(s);
        }
    }
    
    private synchronized void verifyTRSService(Service s) {
        boolean exist = false;
        
        for (int i = 0; i < translation.size(); i++) {
            if (translation.get(i).getIP().equals(s.getIP())
                    && translation.get(i).getPort().equals(s.getPort())) {
                translation.get(i).setLastRegistration(LocalTime.now());
                exist = true;
            }
        }
        
        if (!exist) {
            translation.add(s);
        }
    }
}
