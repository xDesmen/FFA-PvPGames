package cc.pvpgames.ffa.profile;

import cc.pvpgames.ffa.ability.Ability;
import cc.pvpgames.ffa.perk.Perk;
import cc.pvpgames.ffa.utility.Timer;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
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


}
