package nl.DMI.SWS.ATP.Util;

import java.util.concurrent.TimeUnit;

public class TimeBase {
    private long duration;
    private TimeUnit unit;
    private String shortFormat;

    public TimeBase(long duration, TimeUnit unit) {
        updateTimeBase(duration, unit);
    }

    public void updateTimeBase(long duration, TimeUnit unit) {
        this.duration = duration;
        this.unit = unit;
        this.shortFormat = determineShortFormat(unit);
    }

    public long getNano() {
        return unit.toNanos(duration);
    }

    public long getDuration() {
        return duration;
    }

//    public void setDuration(long duration) {
//        this.duration = duration;
//    }

    public TimeUnit getUnit() {
        return unit;
    }

//    public void setUnit(TimeUnit unit) {
//        this.unit = unit;
//        this.shortFormat = determineShortFormat(unit);
//    }

    private String determineShortFormat(TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return "nS";
            case MICROSECONDS:
                return "μs";
            case MILLISECONDS:
                return "ms";
            case SECONDS:
                return "s";
            default:
                return "nS";
        }
    }

    @Override
    public String toString() {
        return String.format("%d %s", duration, shortFormat);
    }
}
