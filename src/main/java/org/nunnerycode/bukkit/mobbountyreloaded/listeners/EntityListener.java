package org.nunnerycode.bukkit.mobbountyreloaded.listeners;

import net.nunnerycode.bukkit.libraries.ivory.utils.HoloUtils;
import net.nunnerycode.bukkit.libraries.ivory.utils.MessageUtils;
import net.nunnerycode.bukkit.libraries.ivory.utils.RandomRangeUtils;
import net.nunnerycode.bukkit.libraries.ivory.utils.StringUtils;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.nunnerycode.bukkit.mobbountyreloaded.MobBountyReloadedPlugin;
import org.nunnerycode.bukkit.mobbountyreloaded.api.TimeOfDay;
import org.nunnerycode.bukkit.mobbountyreloaded.events.MobBountyReloadedRewardEvent;

import java.util.ArrayList;
import java.util.List;

public final class EntityListener implements Listener {

    private MobBountyReloadedPlugin plugin;

    public EntityListener(MobBountyReloadedPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!(player.getLastDamageCause() instanceof EntityDamageByEntityEvent)) {
            double bal = plugin.getEconomyHandler().getBalance(player);
            boolean
                    isPerc =
                    plugin.getIvorySettings().getBoolean("config.on-other-deaths.percentage", true);
            double
                    perc =
                    plugin.getIvorySettings().getDouble("config.on-other-deaths.value", 50.0);
            double amount = (isPerc) ? (perc/ -100D) * bal : -perc;
            plugin.getEconomyHandler().transaction(player, amount);
            if (player.hasPermission("mobbountyreloaded.receive-messages")) {
                MessageUtils.sendColoredArgumentMessage(player, plugin.getIvorySettings()
                                .getString("language.messages.lost-money",
                                        "language.messages.lost-money"),
                        new String[][]{
                                {"%amount%", plugin.getEconomyHandler().format(
                                        Math.abs(amount))}}
                );
            }
            return;
        }
        EntityDamageByEntityEvent eEvent = (EntityDamageByEntityEvent) player.getLastDamageCause();
        if (!(eEvent.getDamager() instanceof LivingEntity)) {
            return;
        }
        LivingEntity damager = (LivingEntity) eEvent.getDamager();
        if (!(damager instanceof Player)) {
            double bal = plugin.getEconomyHandler().getBalance(player);
            boolean
                    isPerc =
                    plugin.getIvorySettings().getBoolean("config.on-death-to-monster-loss.percentage", false);
            double
                    perc =
                    plugin.getIvorySettings().getDouble("config.on-death-to-monster-loss.value", 150.0);
            double amount = (isPerc) ? (perc/ -100D) * bal : -perc;
            plugin.getEconomyHandler().transaction(player, amount);
            if (player.hasPermission("mobbountyreloaded.receive-messages")) {
                MessageUtils.sendColoredArgumentMessage(player, plugin.getIvorySettings()
                                .getString("language.messages.lost-money",
                                        "language.messages.lost-money"),
                        new String[][]{
                                {"%amount%", plugin.getEconomyHandler().format(
                                        Math.abs(amount))}}
                );
            }
        } else {
            double bal = plugin.getEconomyHandler().getBalance(player);
            boolean
                    isPerc =
                    plugin.getIvorySettings().getBoolean("config.on-death-to-player-loss.percentage", false);
            double perc =
                    plugin.getIvorySettings().getDouble("config.on-death-to-player-loss.value", 150.0);
            double amount = (isPerc) ? (perc/ -100D) * bal : -perc;
            plugin.getEconomyHandler().transaction(player, amount);
            if (player.hasPermission("mobbountyreloaded.receive-messages")) {
                MessageUtils.sendColoredArgumentMessage(player, plugin.getIvorySettings()
                                .getString("language.messages.lost-money",
                                        "language.messages.lost-money"),
                        new String[][]{
                                {"%amount%", plugin.getEconomyHandler().format(
                                        Math.abs(amount))}}
                );
            }
            if (plugin.getIvorySettings()
                    .getBoolean("config.on-death-to-player-loss.killer-gains-losses", true)) {
                plugin.getEconomyHandler().transaction((Player) damager, Math.abs(amount));
                if (((Player) damager).hasPermission("mobbountyreloaded.receive-messages")) {
                    MessageUtils.sendColoredArgumentMessage((Player) damager, plugin.getIvorySettings()
                                    .getString("language.messages.gained-money",
                                            "language.messages.gained-money"),
                            new String[][]{
                                    {"%amount%", plugin.getEconomyHandler().format(
                                            Math.abs(amount))}, {"%player%", player.getDisplayName()}}
                    );
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeathEvent(EntityDeathEvent event) {
        if (event instanceof PlayerDeathEvent) {
            return;
        }
        LivingEntity livingEntity = event.getEntity();
        Player player = livingEntity.getKiller();
        if (player == null || !player.hasPermission("mobbountyreloaded.earn")) {
            return;
        }
        ItemStack itemStack = player.getItemInHand();
        DoubleRange rewardRange =
                plugin.getMobHandler().getReward(livingEntity.getType(), livingEntity.getWorld());
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
        double enchMult = 1.0;
        double weatherMult;
        if (player.getWorld().isThundering()) {
            weatherMult = plugin.getIvorySettings().getDouble("multipliers.weather.stormy", 1.0);
        } else {
            weatherMult = plugin.getIvorySettings().getDouble("multipliers.weather.sunny", 1.0);
        }
        double distanceMult = plugin.getIvorySettings().getDouble(
                "multipliers.distance-from-world-spawn." + livingEntity.getWorld().getName()
                        + ".per-10", 0.0
        );
        if (distanceMult == 0.0) {
            distanceMult = plugin.getIvorySettings().getDouble(
                    "multipliers.distance-from-world-spawn.default.per-10", 0.0);
        }
        distanceMult = 1.0 + distanceMult * (event.getEntity().getLocation()
                .distanceSquared(event.getEntity().getWorld().getSpawnLocation()) / 100);

        double groupMult = plugin.getIvorySettings().getDouble("mulipliers.group."
                + plugin.getGroupHandler().getGroup(player), 1.0);

        List<String> enchStrings =
                plugin.getIvorySettings()
                        .getStringList("multipliers.enchantments", new ArrayList<String>());
        for (String s : enchStrings) {
            if (!s.contains(":")) {
                continue;
            }
            String[] split = s.split(":");
            if (split.length < 3) {
                continue;
            }
            Enchantment enchantment = Enchantment.getByName(split[0]);
            if (enchantment == null || !itemStack.containsEnchantment(enchantment)) {
                continue;
            }
            int level = NumberUtils.toInt(split[1]);
            if (itemStack.getEnchantmentLevel(enchantment) != level) {
                continue;
            }
            enchMult *= NumberUtils.toDouble(split[2]);
        }

        double totalMult = biomeMult * timeMult * worldMult * envMult * enchMult * weatherMult * distanceMult *
                           groupMult;

        d *= totalMult;

        MobBountyReloadedRewardEvent mbrre = new MobBountyReloadedRewardEvent(player, livingEntity, d);
        Bukkit.getPluginManager().callEvent(mbrre);

        if (mbrre.isCancelled()) {
            return;
        }

        d = mbrre.getAmount();

        String
                mobName =
                plugin.getIvorySettings().getString("language.mob-names." + livingEntity.getType().name(),
                        livingEntity.getType().name());

        plugin.getEconomyHandler().transaction(player, d);
        if (!player.hasPermission("mobbountyreloaded.receive-messages")) {
            return;
        }
        int holoAPIOffset = plugin.getIvorySettings().getInt("config.holoapi.offset", 3);
        if (d > 0.0) {
            MessageUtils.sendColoredArgumentMessage(player, plugin.getIvorySettings()
                            .getString("language.messages.reward",
                                    "language.messages.reward"),
                    new String[][]{{"%amount%",
                            plugin.getEconomyHandler().format(d)},
                            {"%mob%", mobName}}
            );
            if (plugin.getIvorySettings().getBoolean("config.holoapi.hook", true)) {
                HoloUtils.showHologram(event.getEntity().getEyeLocation().add(0, holoAPIOffset, 0), 3, StringUtils
                        .colorString(StringUtils.replaceArgs(plugin.getIvorySettings()
                                        .getString("language.messages.holo-reward", "language.messages.holo-reward"),
                                new String[][]{{"%value%", plugin.getEconomyHandler().format(d)}}
                        )));
            }
        } else if (d < 0.0) {
            MessageUtils.sendColoredArgumentMessage(player, plugin.getIvorySettings()
                            .getString("language.messages.fine",
                                    "language.messages.fine"),
                    new String[][]{
                            {"%amount%", plugin.getEconomyHandler().format(
                                    Math.abs(d))},
                            {"%mob%", mobName}}
            );
            if (plugin.getIvorySettings().getBoolean("config.holoapi.hook", true)) {
                HoloUtils.showHologram(event.getEntity().getEyeLocation().add(0, holoAPIOffset, 0), 3,
                        StringUtils.colorString(StringUtils.replaceArgs(plugin.getIvorySettings()
                                        .getString("language.messages.holo-reward", "language.messages.holo-fine"),
                                new String[][]{{"%value%", plugin.getEconomyHandler().format(Math.abs(d))}}
                        ))
                );
            }
        }
    }

}
