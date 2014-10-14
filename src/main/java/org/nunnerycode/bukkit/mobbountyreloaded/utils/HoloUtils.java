package org.nunnerycode.bukkit.mobbountyreloaded.utils;

import com.dsh105.holoapi.HoloAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public final class HoloUtils {

    private HoloUtils() {
        // do nothing
    }

    public static void showHologram(Location location, int duration, String message) {
        if (Bukkit.getPluginManager().getPlugin("HoloAPI") == null) {
            return;
        }
        HoloAPI.getManager().createSimpleHologram(location, duration, message);
    }

    public static void showHologram(Location location, int duration, List<String> messages) {
        if (Bukkit.getPluginManager().getPlugin("HoloAPI") == null) {
            return;
        }
        HoloAPI.getManager().createSimpleHologram(location, duration, messages);
    }

}
