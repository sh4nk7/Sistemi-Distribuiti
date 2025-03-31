package distributed;

public class SimulationLogger {
    public static synchronized void log(String message) {
        System.out.println("[" + System.currentTimeMillis() + "] " + message);
    }
}