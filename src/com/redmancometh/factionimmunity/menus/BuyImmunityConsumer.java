package com.redmancometh.factionimmunity.menus;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import com.massivecraft.factions.FPlayers;
import com.redmancometh.factionimmunity.FactionImmunity;
import com.redmancometh.factionimmunity.ImmunityBase;
import com.redmancometh.factionimmunity.ImmunityEntry;
import com.redmancometh.factionimmunity.util.DateDiff;
import com.redmancometh.factionimmunity.util.TimeUtil;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class BuyImmunityConsumer implements BiConsumer<ClickType, Player>
{
    private int price;
    private int duration;
    private Economy eco;
    private Map<Integer, Calendar> calendarMap = new HashMap();

    public BuyImmunityConsumer(int price, int duration)
    {
        super();
        this.price = price;
        this.duration = duration;
    }

    /**
     * Gets or instantiates a calendar from the calendar map based on the duration in minutes
     * @param duration Duration in MINUTES to skip the calendar forward for expiration
     * @return
     */
    public Calendar getExpireCalendar(int duration)
    {
        Calendar calendar = calendarMap.get(duration);
        if (calendar == null)
        {
            calendar = new GregorianCalendar();
            calendar.add(Calendar.MINUTE, duration);
            calendarMap.put(duration, calendar);
        }
        return calendar;
    }

    public Economy getEco()
    {
        if (eco != null)
        {
            return eco;
        }
        try
        {
            return (Economy) Bukkit.getServicesManager().getRegistration(Class.forName("net.milkbowl.vault.economy.Economy")).getProvider();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void accept(ClickType t, Player p)
    {
        String id = FPlayers.getInstance().getByPlayer(p).getFaction().getId();
        System.out.println(id + " in accept " + FPlayers.getInstance().getByPlayer(p).getFaction().getTag());
        ImmunityBase immunityBase = FactionImmunity.getImmuneManager().getImmunityFor(id);
        if (immunityBase instanceof ImmunityEntry)
        {
            long secDiff = TimeUtil.compareTwoTimeStamps(((ImmunityEntry) immunityBase).getExpireStamp(), new Timestamp(Calendar.getInstance().getTime().getTime()));
            DateDiff dateDiff = TimeUtil.splitToComponentTimes(secDiff);
            String msg = FactionImmunity.getCfg().getAlreadyImmune().replace("%m", dateDiff.getDiffMinutes() + "").replace("%h", dateDiff.getDiffHours() + "").replace("%s", dateDiff.getDiffSeconds() + "");
            p.sendMessage(FactionImmunity.getCfg().getPrefix() + msg);
            p.closeInventory();
            return;
        }
        if (!getEco().has(p, price))
        {
            p.sendMessage(FactionImmunity.getCfg().getPrefix() + FactionImmunity.getCfg().getNotEnoughMoney());
            p.closeInventory();
            return;
        }
        EconomyResponse response = getEco().withdrawPlayer(p, price);
        p.sendMessage(FactionImmunity.getCfg().getBoughtImmunity().replace("%p", price + "").replace("%l", response.balance + ""));
        Timestamp stamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        Timestamp expireStamp = new Timestamp(getExpireCalendar(duration).getTime().getTime());
        FactionImmunity.getImmuneManager().insertImmunityEntryFor(id, new ImmunityEntry(id, stamp, expireStamp, duration));

    }

}
