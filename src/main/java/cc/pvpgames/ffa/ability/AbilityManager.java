package cc.pvpgames.ffa.ability;

import cc.pvpgames.ffa.ability.impl.RobinHood;
import cc.pvpgames.ffa.ability.impl.Tank;
import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Data
public class AbilityManager {

    public AbilityManager() {
        setup();
    }

    private Set<Ability> abilities = new HashSet<>();

    private void setup() {
        abilities.add(new RobinHood());
        abilities.add(new Tank());
    }

    public Ability getAbilityByID(String id) {
        return abilities.stream().filter(ability -> ability.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    Ability getAbilityByName(String name) {
        return abilities.stream().filter(ability -> ChatColor.stripColor(ability.displayName).equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public boolean hasAbility(Player player, String id) {
        Ability ability = getAbilityByID(id);
        return ability != null && player.hasPermission(ability.getPermission());
    }

}
