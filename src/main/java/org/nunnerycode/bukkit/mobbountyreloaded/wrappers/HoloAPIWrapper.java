package org.nunnerycode.bukkit.mobbountyreloaded.wrappers;

import com.dsh105.holoapi.HoloAPI;
import net.nunnerycode.bukkit.libraries.ivory.IvoryPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public final class HoloAPIWrapper {

    private HoloAPI holoAPI;

    public HoloAPIWrapper(IvoryPlugin iPlugin) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("HoloAPI");
        if (plugin == null) {
            return;
        }
        holoAPI = (HoloAPI) plugin;
        iPlugin.debug(Level.INFO, "Hooked HoloAPI");
    }

    public void showMessage(Location location, int seconds, String message) {
        if (holoAPI == null) {
            return;
        }
        HoloAPI.getManager().createSimpleHologram(location, seconds, message);
    }

}
