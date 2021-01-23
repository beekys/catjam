package fi.utu.tech.ringersClock.entities;

import java.io.Serializable;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Alarm implements Serializable {
    private static final long serialVersionUID = 1L;
    private Instant time;
    private int hour;
    private int minute;
    private boolean noRain;
    private boolean tempIsPlus;

    public Alarm(int hour, int minute, boolean noRain, boolean tempPlus) {
        this.time = timeToInstant(hour, minute);
        this.noRain = noRain;
        this.tempIsPlus = tempPlus;
    }

    private Instant timeToInstant(int hour, int minute) {
        Instant target = LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute)).atZone(ZoneId.systemDefault()).toInstant();

        if (Instant.now().toEpochMilli() <= target.toEpochMilli()) {
            return target;
        } else {
            return target.plus(1, ChronoUnit.DAYS);
        }
    }

    public Instant getTime() { return this.time; }

    public boolean getNoRain() { return this.noRain; }

    public boolean getTempIsPlus() { return this.tempIsPlus; }

}
