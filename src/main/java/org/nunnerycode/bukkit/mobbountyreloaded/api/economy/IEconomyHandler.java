package org.nunnerycode.bukkit.mobbountyreloaded.api.economy;

import org.bukkit.entity.Player;

public interface IEconomyHandler {

  void give(Player player, double amount);

  void take(Player player, double amount);

  void transaction(Player player, double amount);

  void setBalance(Player player, double amount);

  String format(double amount);

  double getBalance(Player player);

}
