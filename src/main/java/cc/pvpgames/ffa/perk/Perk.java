package cc.pvpgames.ffa.perk;

import lombok.Data;
import org.bukkit.Material;

import java.util.List;

@Data
public class Perk {

    protected String id;
    protected String displayName;
    protected String permission;

    protected Material display;

    protected int cost;
    protected List description;

    public Perk(String id) {
        this.id = id;
    }


}
