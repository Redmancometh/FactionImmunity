package com.redmancometh.factionimmunity.menus;

import com.redmancometh.factionimmunity.ConfigManager;
import com.redmancometh.factionimmunity.FactionImmunity;

public class ImmunityMenu extends Menu
{
    private static ImmunityMenu menu;

    static
    {
        setMenu(new ImmunityMenu());
    }

    public ImmunityMenu()
    {
        super(FactionImmunity.getCfg().getImmunityInvName(), FactionImmunity.getCfg().getMenuSize());
        ConfigManager cfg = FactionImmunity.getCfg();
        FactionImmunity.getInstance().getMenuManagerInstance().addMenu(this);
        this.setButton(cfg.getHourPos(), buildHourImmune());
        this.setButton(cfg.getHalfDayPos(), buildHalfImmune());
        this.setButton(cfg.getFullDayPos(), buildFullImmune());
        decorateMenu(cfg.getDataOne(), cfg.getDataTwo(), cfg.getDataThree());
        //not thrilled about doing this statically, but whateverS
    }

    public static ImmunityMenu getMenu()
    {
        return menu;
    }

    public static void setMenu(ImmunityMenu menu)
    {
        ImmunityMenu.menu = menu;
    }

    public MenuButton buildFullImmune()
    {
        return new MenuButton((p) -> FactionImmunity.getCfg().getFullIcon(), new BuyImmunityConsumer(FactionImmunity.getCfg().getFullCost(), 1440));
    }

    public MenuButton buildHalfImmune()
    {
        return new MenuButton((p) -> FactionImmunity.getCfg().getHalfIcon(), new BuyImmunityConsumer(FactionImmunity.getCfg().getHalfCost(), 720));
    }

    public MenuButton buildHourImmune()
    {
        return new MenuButton((p) -> FactionImmunity.getCfg().getHourIcon(), new BuyImmunityConsumer(FactionImmunity.getCfg().getHourCost(), 60));
    }

}
