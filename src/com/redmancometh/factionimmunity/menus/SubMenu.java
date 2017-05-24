package com.redmancometh.factionimmunity.menus;

import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.redmancometh.factionimmunity.FactionImmunity;

public interface SubMenu
{
    public abstract void close(Player p);

    public default void closeMenu(Player p)
    {
        p.setMetadata("lowermenu", new FixedMetadataValue(FactionImmunity.getInstance(), p));
        close(p);
    }
}
