package cc.pvpgames.ffa.ability;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.ability.impl.RobinHood;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

@Data
public class Ability implements Listener {

    protected String id;
    protected String displayName;
    protected String permission;

    protected List description;

    protected double cost;
    protected int cooldown;

    private List<RobinHood> abilities = new ArrayList<>();

    public Ability(String id) {
        this.id = id;
        setup();
    }

    private void setup() {
        abilities.add(new RobinHood());
        Bukkit.getPluginManager().registerEvents(this, FFAPlugin.getInstance());
    }


}
