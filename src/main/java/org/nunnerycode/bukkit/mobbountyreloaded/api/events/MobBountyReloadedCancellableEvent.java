package org.nunnerycode.bukkit.mobbountyreloaded.api.events;

import org.bukkit.event.Cancellable;

public class MobBountyReloadedCancellableEvent extends MobBountyReloadedEvent implements
                                                                              Cancellable {

  private boolean cancelled;

  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

}
