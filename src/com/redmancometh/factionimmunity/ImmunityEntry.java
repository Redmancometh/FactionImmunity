package com.redmancometh.factionimmunity;

import java.sql.Timestamp;
import java.util.Calendar;

public class ImmunityEntry implements ImmunityBase
{

    /**
     * Duration in minutes
     */
    private int duration;
    private Timestamp timeStamp;
    private Timestamp expireStamp;
    private String factionID; //re-encapsulate despite the outside class having it for convenience

    public ImmunityEntry(String factionID, Timestamp timeStamp, Timestamp expireStamp, int duration)
    {
        super();
        this.duration = duration;
        this.timeStamp = timeStamp;
        this.factionID = factionID;
        this.expireStamp = expireStamp;
    }

    public boolean hasExpired()
    {
        return this.expireStamp.after(Calendar.getInstance().getTime());
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public Timestamp getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public String getFactionID()
    {
        return factionID;
    }

    public void setFactionID(String factionID)
    {
        this.factionID = factionID;
    }

    public Timestamp getExpireStamp()
    {
        return expireStamp;
    }

    public void setExpireStamp(Timestamp expireStamp)
    {
        this.expireStamp = expireStamp;
    }

}
