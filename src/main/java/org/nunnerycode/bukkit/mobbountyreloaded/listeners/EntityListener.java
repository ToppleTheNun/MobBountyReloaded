package org.nunnerycode.bukkit.mobbountyreloaded.listeners;

import net.nunnerycode.bukkit.libraries.ivory.utils.MessageUtils;
import net.nunnerycode.bukkit.libraries.ivory.utils.RandomRangeUtils;

import org.apache.commons.lang.math.DoubleRange;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import org.nunnerycode.bukkit.mobbountyreloaded.api.TimeOfDay;

public final class EntityListener implements Listener {

  private MobBountyReloadedPlugin plugin;

  public EntityListener(MobBountyReloadedPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onEntityDeathEvent(EntityDeathEvent event) {
    LivingEntity livingEntity = event.getEntity();
    Player player = livingEntity.getKiller();
    if (player == null || player.hasPermission("!mobbountyreloaded.earn")) {
      return;
    }
    DoubleRange rewardRange = plugin.getMobHandler().getReward(livingEntity.getType());
    double d =
        RandomRangeUtils.randomRangeDoubleInclusive(rewardRange.getMinimumDouble(),
                                                    rewardRange.getMaximumDouble());

    double biomeMult =
        plugin.getIvorySettings().getDouble(
            "multipliers.biome." + livingEntity.getLocation().getBlock().getBiome().name(), 1.0);
    double timeMult = plugin.getIvorySettings().getDouble(
        "multipliers.time." + TimeOfDay.getTimeOfDay(livingEntity.getWorld().getTime()).name(),
        1.0);
    double worldMult =
        plugin.getIvorySettings().getDouble(
            "multipliers.world." + livingEntity.getWorld().getName(), 1.0);
    double envMult =
        plugin.getIvorySettings()
            .getDouble("multipliers.environment." + livingEntity.getWorld().getEnvironment().name(),
                       1.0);

    d = d * biomeMult * timeMult * worldMult * envMult;

    plugin.getEconomyHandler().transaction(player, d);
    if (d > 0.0) {
      MessageUtils.sendColoredArgumentMessage(player, plugin.getIvorySettings()
          .getString("language.messages.reward", "language.messages.reward"),
                                              new String[][]{{"%amount%",
                                                              plugin.getEconomyHandler().format(d)},
                                                             {"%mob%",
                                                              livingEntity.getType().name()}});
    } else if (d < 0.0) {
      MessageUtils.sendColoredArgumentMessage(player, plugin.getIvorySettings()
          .getString("language.messages.fine", "language.messages.fine"),
                                              new String[][]{
                                                  {"%amount%", plugin.getEconomyHandler().format(
                                                      Math.abs(d))},
                                                  {"%mob%",
                                                   livingEntity.getType().name()}});
    }
  }

}
