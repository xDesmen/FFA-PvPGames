package cc.pvpgames.ffa.ability;

import cc.pvpgames.ffa.FFAPlugin;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Data
public class Ability implements Listener {

    protected String id;
    protected String displayName;
    protected String permission;

    protected List description;

    protected Material display;

    protected int cost;
    protected int cooldown;



    public Ability(String id) {
        this.id = id;
        setup();
    }

    public void setup() {
        Bukkit.getPluginManager().registerEvents(this, FFAPlugin.getInstance());
    }



}
