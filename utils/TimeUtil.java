package KexTools.utils;

public class TimeUtil {
	public long lastMS = 0L;
	private long current;
	
    /**
     * Devides 1000 / d
     */
    public int convertToMS(final int d) {
        return 1000 / d;
    }

    /***
     * Gets current system time
     */
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    public void updateTime() {
        current = (System.nanoTime() / 1000000L);
    }

    public void resetTime() {
        lastMS = (System.nanoTime() / 1000000L);
    }

    public long getElapsedTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    /***
     * Checks if a timer has reached an amount of time
     */
    public boolean hasReached(final long milliseconds) {
        return getCurrentMS() - lastMS >= milliseconds;
    }

    /***
     * Gets the current amount of time that has passed since last reset
     */
    public long getDelay() {
        return System.currentTimeMillis() - lastMS;
    }

    /***
     * Resets timer
     */
    public void reset() {
        lastMS = getCurrentMS();
    }
    
    public void setLastMS() {
        lastMS = System.currentTimeMillis();
    }
    
    public void setLastMS(final long lastMS) {
        this.lastMS = lastMS;
    }

    public static String niggers() {
		return null;
    }
}
