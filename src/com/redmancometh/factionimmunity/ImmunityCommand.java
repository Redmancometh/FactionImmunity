package com.redmancometh.factionimmunity;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.redmancometh.factionimmunity.menus.ImmunityMenu;

public class ImmunityCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player p = (Player) sender;
        if (FPlayers.getInstance().getByPlayer(p).getFaction() == Factions.getInstance().getNone())
        {
            p.sendMessage(FactionImmunity.getCfg().getPrefix() + FactionImmunity.getCfg().getNoFactionMessage());
            return true;
        }
        ((HumanEntity) sender).openInventory(ImmunityMenu.getMenu().getConstructInventory().apply((Player) sender));
        //don't give a shit about console, because only a potato would do that.
        return true;
    }

}
