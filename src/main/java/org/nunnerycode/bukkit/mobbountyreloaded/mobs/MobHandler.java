package org.nunnerycode.bukkit.mobbountyreloaded.mobs;

import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import org.nunnerycode.bukkit.mobbountyreloaded.api.mobs.IMobHandler;
import org.nunnerycode.bukkit.mobbountyreloaded.wrappers.RangeWrapper;

public final class MobHandler implements IMobHandler {

    private MobBountyReloadedPlugin plugin;

    public MobHandler(MobBountyReloadedPlugin plugin) {
        this.plugin = plugin;
    }

    public MobBountyReloadedPlugin getPlugin() {
        return plugin;
    }

    @Override
    public DoubleRange getReward(EntityType entityType, World world) {
        String s;
        if (world == null) {
            s = plugin.getIvorySettings().getString("rewards.default." + entityType.name(), "0:0");
        } else {
            s = plugin.getIvorySettings().getString("rewards." + world.getName() + "." + entityType.name(), "0:0");
            if (s.equals("0:0")) {
                s = plugin.getIvorySettings().getString("rewards.default." + entityType.name(), "0:0");
            }
        }
        String[] array = s.split(":");
        DoubleRange dr;
        if (array.length == 0) {
            dr = new DoubleRange(0);
        } else if (array.length == 1) {
            dr = new DoubleRange(NumberUtils.toDouble(array[0], 0));
        } else {
            dr = new DoubleRange(NumberUtils.toDouble(array[0], 0), NumberUtils.toDouble(array[1], 0));
        }
        return dr;
    }

    @Override
    public void setReward(EntityType entityType, World world, String reward) {
        if (world == null) {
            plugin.getIvorySettings().set("rewards.default." + entityType.name(), reward);
        } else {
            plugin.getIvorySettings().set("rewards." + world.getName() + "." + entityType.name(), reward);
        }
    }

    @Override
    public void setReward(EntityType entityType, World world, DoubleRange reward) {
        setReward(entityType, world, new RangeWrapper(reward).toString());
    }

}
