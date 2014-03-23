package org.nunnerycode.bukkit.mobbountyreloaded.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobBountyReloadedEvent extends Event {

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  private static final HandlerList HANDLER_LIST = new HandlerList();

  @Override
  public HandlerList getHandlers() {
    return HANDLER_LIST;
  }

}
