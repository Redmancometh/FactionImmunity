package com.redmancometh.factionimmunity.util;

public class TimeUtil
{
    public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime)
    {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();
        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        return diffSeconds;
    }

    public static DateDiff splitToComponentTimes(long biggy)
    {
        int hours = (int) biggy / 3600;
        int remainder = (int) biggy - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        return new DateDiff(secs, mins, hours);
    }
}
