package org.nunnerycode.bukkit.mobbountyreloaded.api.mobs;

import org.apache.commons.lang.math.DoubleRange;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

public interface IMobHandler {

  DoubleRange getReward(EntityType entityType, World world);

  void setReward(EntityType entityType, World world, String reward);

  void setReward(EntityType entityType, World world, DoubleRange reward);

}
