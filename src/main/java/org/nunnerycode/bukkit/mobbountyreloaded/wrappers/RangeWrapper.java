package org.nunnerycode.bukkit.mobbountyreloaded.wrappers;

import org.apache.commons.lang.math.Range;

public final class RangeWrapper {

  private final Range range;

  public RangeWrapper(Range range) {
    this.range = range;
  }

  public Range getRange() {
    return range;
  }

  @Override
  public String toString() {
    return range.getMinimumNumber() + ":" + range.getMaximumNumber();
  }

}
