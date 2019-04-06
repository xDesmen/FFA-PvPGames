package cc.pvpgames.ffa.ability.impl;

import cc.pvpgames.ffa.ability.Ability;
import cc.pvpgames.ffa.utility.Color;
import org.bukkit.Material;
import java.util.Arrays;
import java.util.Collections;

public class Tank extends Ability {

    public Tank() {
        super("Tank");
        setup();
    }

    public void setup() {
        setCost(450);
        setPermission("ffa.ability.tank");
        setCooldown(30);
        setDescription(Collections.singletonList("&7Getting attacked will give you resistance 2 for 4 seconds."));
        setDisplayName(Color.translate("&3Tank"));
        setDisplay(Material.IRON_CHESTPLATE);
    }

}
