package cc.pvpgames.ffa.profile;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.ability.Ability;
import cc.pvpgames.ffa.perk.Perk;
import cc.pvpgames.ffa.utility.Timer;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.UUID;

@Data
public class Profile {

    private UUID uuid;

    private int kills, deaths, gold;

    private Ability ability;
    private Perk perk;
    private Timer abilityCooldown;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }


    public String getActiveAbility() {
        return getAbility() == null ? "None" : getAbility().getDisplayName();
    }

    public String getActivePerk() {
        return getPerk() == null ? "None" : getPerk().getDisplayName();
    }

    public boolean hasAbility(Ability ability) {
        return getPlayer().hasPermission(ability.getPermission());
    }
    public boolean isActiveAbility(String id) {
        Ability ability = FFAPlugin.getInstance().getAbilityManager().getAbilityByID(id);
        return ability != null && ability.getId().equalsIgnoreCase(id);
    }

    public boolean hasPerk(Perk perk) {
        return getPlayer().hasPermission(perk.getPermission());
    }
    public boolean isActivePerk(String id) {
        Perk perk = FFAPlugin.getInstance().getPerkManager().getPerkByID(id);
        return ability != null && ability.getId().equalsIgnoreCase(id);
    }



}
