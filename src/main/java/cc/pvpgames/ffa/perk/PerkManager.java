package cc.pvpgames.ffa.perk;

import cc.pvpgames.ffa.perk.impl.Multiply;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
@Data
public class PerkManager {

    public PerkManager() {
        setup();
    }

    private Set<Perk> perks = new HashSet<>();

    private void setup() {
        perks.add(new Multiply());
    }

    public Perk getPerkByID(String id) {
        return perks.stream().filter(perk -> perk.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public boolean hasPerk(Player player, String id) {
        Perk perk = getPerkByID(id);
        return perk != null && player.hasPermission(perk.getPermission());
    }

    public Perk getPerkByName(String name) {
        return perks.stream().filter(perk -> ChatColor.stripColor(perk.displayName).equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
