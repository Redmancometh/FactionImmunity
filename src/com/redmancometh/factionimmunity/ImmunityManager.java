package com.redmancometh.factionimmunity;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class ImmunityManager
{

    private LoadingCache<String, ImmunityBase> immunityCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).build(new CacheLoader<String, ImmunityBase>()
    {
        @Override
        public ImmunityBase load(String key)
        {
            return FactionImmunity.getDB().getImmunityFor(key);
        }
    });

    public void insertImmunityEntryFor(String factionID, ImmunityEntry entry)
    {
        immunityCache.put(factionID, entry);
        FactionImmunity.getDB().insertImmunity(entry);
    }

    public ImmunityBase getImmunityFor(String factionId)
    {
        try
        {
            return immunityCache.get(factionId);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        return null; // never return null that's the purpose of BlankImmunityEntry
    }

    public void purgeAndSave(String factionID)
    {
        if (immunityCache.asMap().containsKey(factionID))
        {
            try
            {
                ImmunityBase base = immunityCache.get(factionID);
                if (base instanceof ImmunityEntry) FactionImmunity.getDB().insertImmunity((ImmunityEntry) base);
                immunityCache.asMap().remove(factionID);
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
    }

}
