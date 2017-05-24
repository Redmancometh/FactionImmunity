package com.redmancometh.factionimmunity.databasing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CompletableFuture;

import com.redmancometh.factionimmunity.BlankImmunityEntry;
import com.redmancometh.factionimmunity.FactionImmunity;
import com.redmancometh.factionimmunity.ImmunityBase;
import com.redmancometh.factionimmunity.ImmunityEntry;

public class DatabaseManager
{
    public DatabaseManager()
    {
        createTable();
    }

    public Connection getConnection()
    {
        return FactionImmunity.getPoolConn();
    }

    public void createTable()
    {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("create table if not exists " + FactionImmunity.getCfg().getTableName() + "(faction_id varchar(64) NOT NULL PRIMARY KEY, time_initiated TIMESTAMP, time_expire TIMESTAMP, duration int)"))
        {
            ps.execute();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param key
     * @return
     */
    public ImmunityBase getImmunityFor(String key)
    {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("SELECT duration, time_initiated, time_expire from " + FactionImmunity.getCfg().getTableName() + " where faction_id=?"))
        {
            ps.setString(1, key);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) return new ImmunityEntry(key, rs.getTimestamp("time_initiated"), rs.getTimestamp("time_expire"), rs.getInt("duration"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new BlankImmunityEntry();
    }

    public void insertImmunity(ImmunityEntry entry)
    {
        CompletableFuture.runAsync(() ->
        {
            try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("INSERT INTO " + FactionImmunity.getCfg().getTableName() + "(faction_id, time_initiated, time_expire, duration) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE time_initiated=?, time_expire=?"))
            {
                ps.setString(1, entry.getFactionID());
                ps.setTimestamp(2, entry.getTimeStamp());
                ps.setTimestamp(3, entry.getExpireStamp());
                ps.setInt(4, entry.getDuration());
                ps.setTimestamp(5, entry.getTimeStamp());
                ps.setTimestamp(6, entry.getExpireStamp());
                ps.execute();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }, FactionImmunity.getInstance().getPool());
    }

}
