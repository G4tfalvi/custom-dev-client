package KexTools.utils.time;

public class Stopwatch {
	
private long currentTime;
    
    public Stopwatch() {
        setCurrentTime(getCurrentTime());
    }

    private long getCurrentTime() {
        return System.nanoTime() / 1000000;
    }

    public void resetTime() {
        setCurrentTime(getCurrentTime());
    }
    
    public boolean finished(final long delay) {
        return System.currentTimeMillis() - delay >= currentTime;
    }

    public long getStartTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public boolean timeElapsed(long milliseconds) {
        return getCurrentTime() - getStartTime() >= milliseconds;
    }
}
