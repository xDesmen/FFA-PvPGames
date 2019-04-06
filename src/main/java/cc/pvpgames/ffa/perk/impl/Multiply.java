package cc.pvpgames.ffa.perk.impl;

import cc.pvpgames.ffa.perk.Perk;
import cc.pvpgames.ffa.utility.Color;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.Arrays;

public class Multiply extends Perk {

    public Multiply() {
        super("Multiply");
        setup();
    }


    public void setup() {
        setCost(200);
        setPermission("ffa.perk.multiply");
        setDescription(Arrays.asList("&7Cost: &a" + getCost(), "&7Drop double the gold when you kill someone."));
        setDisplayName(Color.translate("&6Multiply"));
        setDisplay(Material.GOLD_INGOT);
    }


}
