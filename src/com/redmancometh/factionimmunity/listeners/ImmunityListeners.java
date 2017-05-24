package com.redmancometh.factionimmunity.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.redmancometh.factionimmunity.FactionImmunity;
import com.redmancometh.factionimmunity.ImmunityBase;
import com.redmancometh.factionimmunity.ImmunityEntry;

public class ImmunityListeners implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onTNTExplode(EntityExplodeEvent e)
    {
        FLocation fLoc = new FLocation(e.getEntity().getLocation());
        Faction faction = Board.getInstance().getFactionAt(fLoc);
        String factionID = faction.getId();
        ImmunityBase base = FactionImmunity.getImmuneManager().getImmunityFor(factionID);
        if (base instanceof ImmunityEntry)
        {
            e.setYield(0F);
            e.setCancelled(true);
            e.getEntity().remove();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTNTExplode(ExplosionPrimeEvent e)
    {
        FLocation fLoc = new FLocation(e.getEntity().getLocation());
        Faction faction = Board.getInstance().getFactionAt(fLoc);
        String factionID = faction.getId();
        ImmunityBase base = FactionImmunity.getImmuneManager().getImmunityFor(factionID);
        if (base instanceof ImmunityEntry)
        {
            e.setCancelled(true);
            e.setRadius(0);
            e.getEntity().remove();
        }

    }

}
