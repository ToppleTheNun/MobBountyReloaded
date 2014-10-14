package org.nunnerycode.bukkit.mobbountyreloaded.groups;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.nunnerycode.bukkit.mobbountyreloaded.api.groups.IGroupHandler;

public final class GroupHandler implements IGroupHandler {

    private Permission permissions;

    public GroupHandler(Permission permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getGroup(Player player) {
        if (permissions == null) {
            return "";
        }
        if (!permissions.hasGroupSupport()) {
            return "";
        }
        return permissions.getPrimaryGroup(player);
    }

}
