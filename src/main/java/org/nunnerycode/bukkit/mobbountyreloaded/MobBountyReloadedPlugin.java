package org.nunnerycode.bukkit.mobbountyreloaded;

import net.milkbowl.vault.economy.Economy;
import net.nunnerycode.bukkit.libraries.ivory.config.VersionedIvoryYamlConfiguration;
import net.nunnerycode.bukkit.libraries.ivory.settings.IvorySettings;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.nunnerycode.bukkit.mobbountyreloaded.api.economy.IEconomyHandler;
import org.nunnerycode.bukkit.mobbountyreloaded.api.mobs.IMobHandler;
import org.nunnerycode.bukkit.mobbountyreloaded.commands.MobBountyCommands;
import org.nunnerycode.bukkit.mobbountyreloaded.economy.EconomyHandler;
import org.nunnerycode.bukkit.mobbountyreloaded.listeners.EntityListener;
import org.nunnerycode.bukkit.mobbountyreloaded.mobs.MobHandler;

import se.ranzdo.bukkit.methodcommand.CommandHandler;

import java.io.File;
import java.util.logging.Level;

import static net.nunnerycode.bukkit.libraries.ivory.config.VersionedIvoryConfiguration.VersionUpdateType;

public final class MobBountyReloadedPlugin extends JavaPlugin {

  private DebugPrinter debugPrinter;
  private IvorySettings ivorySettings;
  private IMobHandler mobHandler;
  private IEconomyHandler economyHandler;
  private EntityListener entityListener;
  private VersionedIvoryYamlConfiguration rewardsYAML;
  private VersionedIvoryYamlConfiguration multipliersYAML;
  private VersionedIvoryYamlConfiguration languageYAML;

  @Override
  public void onEnable() {
    debugPrinter = new DebugPrinter(getDataFolder().getPath(), "debug.log");

    rewardsYAML =
        new VersionedIvoryYamlConfiguration(new File(getDataFolder(), "rewards.yml"),
                                            getResource("rewards.yml"),
                                            VersionUpdateType.BACKUP_AND_UPDATE);
    if (rewardsYAML.update()) {
      Bukkit.getLogger().info("Updating rewards.yml");
      debug(Level.INFO, "Updating rewards.yml");
    }

    multipliersYAML =
        new VersionedIvoryYamlConfiguration(new File(getDataFolder(), "multipliers.yml"),
                                            getResource("multipliers.yml"),
                                            VersionUpdateType.BACKUP_AND_UPDATE);
    if (multipliersYAML.update()) {
      Bukkit.getLogger().info("Updating multipliers.yml");
      debug(Level.INFO, "Updating multipliers.yml");
    }

    languageYAML =
        new VersionedIvoryYamlConfiguration(new File(getDataFolder(), "language.yml"),
                                            getResource("language.yml"),
                                            VersionUpdateType.BACKUP_AND_UPDATE);
    if (languageYAML.update()) {
      Bukkit.getLogger().info("Updating language.yml");
      debug(Level.INFO, "Updating language.yml");
    }

    ivorySettings = IvorySettings.loadFromFiles(rewardsYAML, multipliersYAML, languageYAML);

    mobHandler = new MobHandler(this);

    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      debug(Level.SEVERE, "Could not find Vault, disabling");
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }
    RegisteredServiceProvider<Economy>
        rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      debug(Level.SEVERE, "Could not find Economy provider, disabling");
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }
    if (rsp.getProvider() == null) {
      debug(Level.SEVERE, "Could not find Economy plugin, disabling");
      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }
    economyHandler = new EconomyHandler(rsp.getProvider());

    entityListener = new EntityListener(this);
    Bukkit.getPluginManager().registerEvents(entityListener, this);

    CommandHandler commandHandler = new CommandHandler(this);
    commandHandler.registerCommands(new MobBountyCommands(this));

    debug(Level.INFO, "v" + getDescription().getVersion() + " enabled");
  }

  public void debug(Level level, String... messages) {
    if (debugPrinter != null) {
      debugPrinter.debug(level, messages);
    }
  }

  public IvorySettings getIvorySettings() {
    return ivorySettings;
  }

  public IMobHandler getMobHandler() {
    return mobHandler;
  }

  public IEconomyHandler getEconomyHandler() {
    return economyHandler;
  }

  public VersionedIvoryYamlConfiguration getRewardsYAML() {
    return rewardsYAML;
  }

  public VersionedIvoryYamlConfiguration getMultipliersYAML() {
    return multipliersYAML;
  }

  public VersionedIvoryYamlConfiguration getLanguageYAML() {
    return languageYAML;
  }

}