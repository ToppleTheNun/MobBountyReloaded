package org.nunnerycode.bukkit.mobbountyreloaded.utils;

import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public final class MobUtils {

    private MobUtils() {
        // do nothing
    }

    public static List<String> getMobsOnServer() {
        List<String> mobs = new ArrayList<>();
        for (EntityType et : EntityType.values()) {
            switch (et) {
                case DROPPED_ITEM:
                case EXPERIENCE_ORB:
                case LEASH_HITCH:
                case PAINTING:
                case ARROW:
                case SNOWBALL:
                case FIREBALL:
                case SMALL_FIREBALL:
                case ENDER_PEARL:
                case ENDER_SIGNAL:
                case THROWN_EXP_BOTTLE:
                case ITEM_FRAME:
                case WITHER_SKULL:
                case PRIMED_TNT:
                case FALLING_BLOCK:
                case FIREWORK:
                case MINECART_COMMAND:
                case BOAT:
                case MINECART:
                case MINECART_CHEST:
                case MINECART_FURNACE:
                case MINECART_TNT:
                case MINECART_HOPPER:
                case MINECART_MOB_SPAWNER:
                case ENDER_CRYSTAL:
                case SPLASH_POTION:
                case EGG:
                case FISHING_HOOK:
                case LIGHTNING:
                case COMPLEX_PART:
                case UNKNOWN:
                case WEATHER:
                    break;
                default:
                    mobs.add(et.name());
                    break;
            }
        }
        return mobs;
    }

}
