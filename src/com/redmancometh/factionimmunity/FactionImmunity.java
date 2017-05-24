package com.redmancometh.factionimmunity;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.redmancometh.factionimmunity.databasing.DataSource;
import com.redmancometh.factionimmunity.databasing.DatabaseManager;
import com.redmancometh.factionimmunity.listeners.ImmunityListeners;
import com.redmancometh.factionimmunity.listeners.ChunkListeners;
import com.redmancometh.factionimmunity.menus.MenuListeners;
import com.redmancometh.factionimmunity.menus.MenuManager;

public class FactionImmunity extends JavaPlugin
{

    private MenuManager menuManager;
    private DatabaseManager databaseManager;
    private Executor pool = Executors.newFixedThreadPool(8, new ThreadFactoryBuilder().setNameFormat("immunity-thread-%d").build());

    private DataSource dataSource;
    private ConfigManager configManager;
    private ImmunityManager immunityManager;

    public void onEnable()
    {
        configManager = new ConfigManager(this);
        configManager.init();
        this.immunityManager = new ImmunityManager();
        this.menuManager = new MenuManager();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MenuListeners(), this);
        pm.registerEvents(new ChunkListeners(), this);
        pm.registerEvents(new ImmunityListeners(), this);
        try
        {
            Bukkit.getLogger().log(Level.INFO, ChatColor.BLUE + "Faction Immunity has started up! Initializing datasources...");
            dataSource = new DataSource(configManager.getHost(), configManager.getPort(), configManager.getDatabase(), configManager.getUser(), configManager.getPassword());
            try (Connection ignored = dataSource.getConnection())
            {
                Bukkit.getLogger().log(Level.INFO, ChatColor.BLUE + "Faction Immunity datasource initialized!");
            }
        }
        catch (SQLException | IOException | PropertyVetoException e)
        {
            e.printStackTrace();
            for (int x = 0; x < 5; x++)
            {
                Bukkit.getLogger().log(Level.SEVERE, ChatColor.DARK_RED + "[Faction Immunity]: Could not acquire datasource connection with SQL DB! Shutting down to prevent exploitation.");
            }
        }
        this.databaseManager = new DatabaseManager();
        getCommand("immunity").setExecutor(new ImmunityCommand());
    }

    public static FactionImmunity getInstance()
    {
        return (FactionImmunity) Bukkit.getPluginManager().getPlugin("FactionImmunity");
    }

    public static Connection getPoolConn()
    {
        return getInstance().getConnection();
    }

    public Connection getConnection()
    {
        try
        {
            return this.dataSource.getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static MenuManager getMenus()
    {
        return getInstance().getMenuManagerInstance();
    }

    public static ConfigManager getCfg()
    {
        return getInstance().getConfigManager();
    }

    public MenuManager getMenuManagerInstance()
    {
        return menuManager;
    }

    public void setMenuManager(MenuManager menuManager)
    {
        this.menuManager = menuManager;
    }

    public DatabaseManager getDatabaseManager()
    {
        return databaseManager;
    }

    public void setDatabaseManager(DatabaseManager databaseManager)
    {
        this.databaseManager = databaseManager;
    }

    public Executor getPool()
    {
        return pool;
    }

    public void setPool(Executor pool)
    {
        this.pool = pool;
    }

    public DataSource getDataSource()
    {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public ImmunityManager getImmunityManagerInstance()
    {
        return this.immunityManager;
    }

    public static ImmunityManager getImmuneManager()
    {
        return getInstance().getImmunityManagerInstance();
    }

    public static DatabaseManager getDB()
    {
        return getInstance().getDatabaseManager();
    }

    public MenuManager getMenuManager()
    {
        return menuManager;
    }

    public ImmunityManager getImmunityManager()
    {
        return immunityManager;
    }

    public void setImmunityManager(ImmunityManager immunityManager)
    {
        this.immunityManager = immunityManager;
    }

    public ConfigManager getConfigManager()
    {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager)
    {
        this.configManager = configManager;
    }

}
