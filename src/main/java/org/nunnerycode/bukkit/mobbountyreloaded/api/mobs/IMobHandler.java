package org.nunnerycode.bukkit.mobbountyreloaded.api.mobs;

import org.apache.commons.lang.math.DoubleRange;
import org.bukkit.entity.EntityType;

public interface IMobHandler {

  DoubleRange getReward(EntityType entityType);

  void setReward(EntityType entityType, String reward);

  void setReward(EntityType entityType, DoubleRange reward);

}
