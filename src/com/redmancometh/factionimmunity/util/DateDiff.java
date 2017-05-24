package com.redmancometh.factionimmunity.util;

public class DateDiff
{
    private long diffHours;
    private long diffMinutes;
    private long diffSeconds;

    public DateDiff(long seconds, long minutes, long hours)
    {
        this.diffHours = hours;
        this.diffMinutes = minutes;
        this.setDiffSeconds(seconds);
    }

    public long getDiffHours()
    {
        return diffHours;
    }

    public void setDiffHours(int diffHours)
    {
        this.diffHours = diffHours;
    }

    public long getDiffMinutes()
    {
        return diffMinutes;
    }

    public void setDiffMinutes(int diffMinutes)
    {
        this.diffMinutes = diffMinutes;
    }

    public long getDiffSeconds()
    {
        return diffSeconds;
    }

    public void setDiffSeconds(long diffSeconds)
    {
        this.diffSeconds = diffSeconds;
    }

}
