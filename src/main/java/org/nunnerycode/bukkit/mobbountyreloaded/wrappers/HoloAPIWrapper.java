package org.nunnerycode.bukkit.mobbountyreloaded.wrappers;

import com.dsh105.holoapi.HoloAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public final class HoloAPIWrapper {

    private HoloAPI holoAPI;

    public HoloAPIWrapper() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("HoloAPI");
        if (plugin == null) {
            return;
        }
        holoAPI = (HoloAPI) plugin;
    }

    public void showMessage(Location location, int seconds, String message) {
        if (holoAPI == null) {
            return;
        }
        HoloAPI.getManager().createSimpleHologram(location, seconds, message);
    }

}
