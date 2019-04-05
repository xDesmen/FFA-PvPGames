package cc.pvpgames.ffa.profile;

import cc.pvpgames.ffa.ability.Ability;
import cc.pvpgames.ffa.perk.Perk;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class Profile {

    private UUID uuid;

    private int kills, deaths, gold;

    private Ability ability;
    private Perk perk;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }


}
