package org.nunnerycode.bukkit.mobbountyreloaded.api;

public enum TimeOfDay {

    DAY("Day", 0, 10000), SUNSET("Sunset", 10000, 1500), NIGHT("Night", 11500, 7000),
    SUNRISE("Sunrise", 18500, 1500);

    private final String name;
    private final int starts;
    private final int length;

    private TimeOfDay(String name, int starts, int length) {
        this.name = name;
        this.starts = starts;
        this.length = length;
    }

    public static TimeOfDay getTimeFromString(String time) {
        if (time.equalsIgnoreCase("day")) {
            return DAY;
        }
        if (time.equalsIgnoreCase("sunset")) {
            return SUNSET;
        }
        if (time.equalsIgnoreCase("night")) {
            return NIGHT;
        }
        if (time.equalsIgnoreCase("sunrise")) {
            return SUNRISE;
        }
        return null;
    }

    public static TimeOfDay getTimeOfDay(long time) {
        if (time >= 0 && time < 10000) {
            return DAY;
        }
        if (time >= 10000 && time < 11500) {
            return SUNSET;
        }
        if (time >= 11500 && time < 18500) {
            return NIGHT;
        }
        return SUNRISE;
    }

    public String getName() {
        return name;
    }

    public int getStarts() {
        return starts;
    }

    public int getLength() {
        return length;
    }

}
