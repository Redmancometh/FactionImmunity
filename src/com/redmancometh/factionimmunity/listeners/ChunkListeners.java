package com.redmancometh.factionimmunity.listeners;

import java.util.concurrent.CompletableFuture;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.redmancometh.factionimmunity.FactionImmunity;

public class ChunkListeners implements Listener
{
    @EventHandler
    public void onLoad(ChunkLoadEvent e)
    {
        FLocation fLoc = new FLocation(e.getChunk().getBlock(0, 0, 0));
        Faction faction = Board.getInstance().getFactionAt(fLoc);
        String factionID = faction.getId();
        CompletableFuture.runAsync(() -> FactionImmunity.getImmuneManager().getImmunityFor(factionID), FactionImmunity.getInstance().getPool());
    }

    @EventHandler
    public void onLoad(ChunkUnloadEvent e)
    {
        FLocation fLoc = new FLocation(e.getChunk().getBlock(0, 0, 0));
        Faction faction = Board.getInstance().getFactionAt(fLoc);
        String factionID = faction.getId();
        FactionImmunity.getImmuneManager().purgeAndSave(factionID);
    }
}
