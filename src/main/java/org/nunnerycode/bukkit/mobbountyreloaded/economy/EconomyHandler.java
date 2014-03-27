package org.nunnerycode.bukkit.mobbountyreloaded.economy;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.entity.Player;
import org.nunnerycode.bukkit.mobbountyreloaded.api.economy.IEconomyHandler;

import java.text.DecimalFormat;

public final class EconomyHandler implements IEconomyHandler {

  private Economy economy;
  private DecimalFormat decimalFormat = new DecimalFormat("#.##");

  public EconomyHandler(Economy economy) {
    this.economy = economy;
  }

  @Override
  public void give(Player player, double amount) {
    if (economy != null) {
      economy.depositPlayer(player.getName(), Math.abs(amount));
    }
  }

  @Override
  public void take(Player player, double amount) {
    if (economy != null) {
      economy.withdrawPlayer(player.getName(), Math.abs(amount));
    }
  }

  @Override
  public void transaction(Player player, double amount) {
    if (amount > 0.0) {
      give(player, amount);
    } else if (amount < 0.0) {
      take(player, amount);
    }
  }

  @Override
  public void setBalance(Player player, double amount) {
    if (economy == null) {
      return;
    }
    transaction(player, -economy.getBalance(player.getName()));
    transaction(player, amount);
  }

  @Override
  public String format(double amount) {
    if (economy == null) {
      return decimalFormat.format(amount);
    }
    return economy.format(amount);
  }

  @Override
  public double getBalance(Player player) {
    if (economy == null) {
      return 0;
    }
    return economy.getBalance(player.getName());
  }

}
