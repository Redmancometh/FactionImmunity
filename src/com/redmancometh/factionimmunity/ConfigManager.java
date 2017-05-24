package com.redmancometh.factionimmunity;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.redmancometh.factionimmunity.util.ItemUtil;
import com.redmancometh.factionimmunity.util.LoreUtil;

public class ConfigManager
{
    //Database info
    private String host;
    private String database;
    private String user;
    private String password;
    private String tableName;
    private int port;
    //Our own FC instance
    private FileConfiguration config;
    private String prefix;
    //All this fucking menu shit
    private String immunityInvName;
    private String sixtyMinName;
    private List<String> sixtyMinLore;
    private String halfDayName;
    private List<String> halfDayLore;
    private String fullDayName;
    private List<String> fullDayLore;
    private int hourPos;
    private int halfDayPos;
    private int fullDayPos;
    private ItemStack hourIcon;
    private ItemStack halfIcon;
    private ItemStack fullIcon;
    private int hourCost;
    private int halfCost;
    private int fullCost;
    private int menuSize;
    private short dataOne;
    private short dataTwo;
    private short dataThree;
    private String notEnoughMoney;
    private String noFactionMessage;
    private String alreadyImmune;
    private String boughtImmunity;

    public ConfigManager(JavaPlugin pl)
    {
        File configFile = new File(pl.getDataFolder(), "immunity.yml");
        if (!configFile.exists())
        {
            pl.saveResource("immunity.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void initDBInfo()
    {
        this.user = config.getString("SQL.username");
        this.password = config.getString("SQL.password");
        this.database = config.getString("SQL.database");
        this.setHost(config.getString("SQL.host"));
        this.port = config.getInt("SQL.port", 3306);
        if (config.isSet("SQL.table_name")) setTableName(config.getString("SQL.table_name"));
    }

    public void init()
    {
        initDBInfo();
        initMessageInfo();
        initMenu();
    }

    public void initMenu()
    {
        this.immunityInvName = ChatColor.translateAlternateColorCodes('&', config.getString("Menu.menu_name"));
        this.sixtyMinName = ChatColor.translateAlternateColorCodes('&', config.getString("Menu.hour_button_name"));
        this.sixtyMinLore = LoreUtil.colorizeLore(config.getStringList("Menu.hour_button_lore"));
        this.hourPos = config.getInt("Menu.hour_button_pos");
        this.halfDayName = ChatColor.translateAlternateColorCodes('&', config.getString("Menu.half_day_name"));
        this.halfDayLore = LoreUtil.colorizeLore(config.getStringList("Menu.half_day_lore"));
        this.halfDayPos = config.getInt("Menu.half_day_button_pos");
        this.fullDayName = ChatColor.translateAlternateColorCodes('&', config.getString("Menu.full_day_name"));
        this.fullDayLore = LoreUtil.colorizeLore(config.getStringList("Menu.full_day_lore"));
        this.fullDayPos = config.getInt("Menu.full_day_button_pos");
        this.fullIcon = ItemUtil.buildItem(Material.valueOf(config.getString("Menu.full_day_material").toUpperCase()), ChatColor.translateAlternateColorCodes('&', config.getString("Menu.full_day_name")), LoreUtil.colorizeLore(config.getStringList("Menu.full_day_lore")));
        this.halfIcon = ItemUtil.buildItem(Material.valueOf(config.getString("Menu.half_day_material").toUpperCase()), ChatColor.translateAlternateColorCodes('&', config.getString("Menu.half_day_name")), LoreUtil.colorizeLore(config.getStringList("Menu.half_day_lore")));
        this.hourIcon = ItemUtil.buildItem(Material.valueOf(config.getString("Menu.hour_material").toUpperCase()), ChatColor.translateAlternateColorCodes('&', config.getString("Menu.hour_button_name")), LoreUtil.colorizeLore(config.getStringList("Menu.hour_lore")));
        this.hourCost = config.getInt("Menu.hour_cost");
        this.halfCost = config.getInt("Menu.half_cost");
        this.fullCost = config.getInt("Menu.full_cost");
        this.menuSize = config.getInt("Menu.inventory_size");
        this.dataOne = (short) config.getInt("Menu.data_one");
        this.dataTwo = (short) config.getInt("Menu.data_two");
        this.dataThree = (short) config.getInt("Menu.data_three");
    }

    public void initMessageInfo()
    {
        this.prefix = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.prefix"));
        this.notEnoughMoney = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.not_enough_money"));
        this.noFactionMessage = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.no_faction"));
        this.alreadyImmune = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.already_immune"));
        this.boughtImmunity = ChatColor.translateAlternateColorCodes('&', config.getString("Messages.bought_immunity"));
    }

    public FileConfiguration getBukkitConfig()
    {
        return config;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getDatabase()
    {
        return database;
    }

    public void setDatabase(String database)
    {
        this.database = database;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getImmunityInvName()
    {
        return immunityInvName;
    }

    public void setImmunityInvName(String immunityInvName)
    {
        this.immunityInvName = immunityInvName;
    }

    public FileConfiguration getConfig()
    {
        return config;
    }

    public void setConfig(FileConfiguration config)
    {
        this.config = config;
    }

    public String getSixtyMinName()
    {
        return sixtyMinName;
    }

    public void setSixtyMinName(String sixtyMinName)
    {
        this.sixtyMinName = sixtyMinName;
    }

    public List<String> getSixtyMinLore()
    {
        return sixtyMinLore;
    }

    public void setSixtyMinLore(List<String> sixtyMinLore)
    {
        this.sixtyMinLore = sixtyMinLore;
    }

    public String getHalfDayName()
    {
        return halfDayName;
    }

    public void setHalfDayName(String halfDayName)
    {
        this.halfDayName = halfDayName;
    }

    public List<String> getHalfDayLore()
    {
        return halfDayLore;
    }

    public void setHalfDayLore(List<String> halfDayLore)
    {
        this.halfDayLore = halfDayLore;
    }

    public String getFullDayName()
    {
        return fullDayName;
    }

    public void setFullDayName(String fullDayName)
    {
        this.fullDayName = fullDayName;
    }

    public int getHourPos()
    {
        return hourPos;
    }

    public void setHourPos(int hourPos)
    {
        this.hourPos = hourPos;
    }

    public int getHalfDayPos()
    {
        return halfDayPos;
    }

    public void setHalfDayPos(int halfDayPos)
    {
        this.halfDayPos = halfDayPos;
    }

    public int getFullDayPos()
    {
        return fullDayPos;
    }

    public void setFullDayPos(int fullDayPos)
    {
        this.fullDayPos = fullDayPos;
    }

    public List<String> getFullDayLore()
    {
        return fullDayLore;
    }

    public void setFullDayLore(List<String> fullDayLore)
    {
        this.fullDayLore = fullDayLore;
    }

    public ItemStack getHourIcon()
    {
        return hourIcon;
    }

    public void setHourIcon(ItemStack hourIcon)
    {
        this.hourIcon = hourIcon;
    }

    public ItemStack getHalfIcon()
    {
        return halfIcon;
    }

    public void setHalfIcon(ItemStack halfIcon)
    {
        this.halfIcon = halfIcon;
    }

    public ItemStack getFullIcon()
    {
        return fullIcon;
    }

    public int getHourCost()
    {
        return hourCost;
    }

    public void setHourCost(int hourCost)
    {
        this.hourCost = hourCost;
    }

    public int getHalfCost()
    {
        return halfCost;
    }

    public void setHalfCost(int halfCost)
    {
        this.halfCost = halfCost;
    }

    public int getFullCost()
    {
        return fullCost;
    }

    public void setFullCost(int fullCost)
    {
        this.fullCost = fullCost;
    }

    public void setFullIcon(ItemStack fullIcon)
    {
        this.fullIcon = fullIcon;
    }

    public String getNotEnoughMoney()
    {
        return notEnoughMoney;
    }

    public void setNotEnoughMoney(String notEnoughMoney)
    {
        this.notEnoughMoney = notEnoughMoney;
    }

    public String getNoFactionMessage()
    {
        return noFactionMessage;
    }

    public void setNoFactionMessage(String noFactionMessage)
    {
        this.noFactionMessage = noFactionMessage;
    }

    public String getAlreadyImmune()
    {
        return alreadyImmune;
    }

    public int getMenuSize()
    {
        return menuSize;
    }

    public void setMenuSize(int menuSize)
    {
        this.menuSize = menuSize;
    }

    public short getDataOne()
    {
        return dataOne;
    }

    public void setDataOne(short dataOne)
    {
        this.dataOne = dataOne;
    }

    public short getDataTwo()
    {
        return dataTwo;
    }

    public void setDataTwo(short dataTwo)
    {
        this.dataTwo = dataTwo;
    }

    public short getDataThree()
    {
        return dataThree;
    }

    public void setDataThree(short dataThree)
    {
        this.dataThree = dataThree;
    }

    public void setAlreadyImmune(String alreadyImmune)
    {
        this.alreadyImmune = alreadyImmune;
    }

    public String getBoughtImmunity()
    {
        return boughtImmunity;
    }

    public void setBoughtImmunity(String boughtImmunity)
    {
        this.boughtImmunity = boughtImmunity;
    }

}
