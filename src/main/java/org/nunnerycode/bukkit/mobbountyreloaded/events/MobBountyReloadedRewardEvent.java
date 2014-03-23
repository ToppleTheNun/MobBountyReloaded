package org.nunnerycode.bukkit.mobbountyreloaded.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.nunnerycode.bukkit.mobbountyreloaded.api.events.MobBountyReloadedCancellableEvent;

public final class MobBountyReloadedRewardEvent extends MobBountyReloadedCancellableEvent {

  private final Player player;
  private final LivingEntity entity;
  private double amount;

  public MobBountyReloadedRewardEvent(Player player, LivingEntity entity, double amount) {
    this.player = player;
    this.entity = entity;
    this.amount = amount;
  }

  public LivingEntity getEntity() {
    return entity;
  }

  public Player getPlayer() {
    return player;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

}
