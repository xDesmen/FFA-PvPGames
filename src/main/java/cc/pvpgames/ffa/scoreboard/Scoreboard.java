package cc.pvpgames.ffa.scoreboard;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.profile.Profile;
import cc.pvpgames.ffa.utility.Color;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard implements AssembleAdapter {

    private String TITLE = FFAPlugin.getInstance().getConfig().getString("general.scoreboard-title");

    @Override
    public String getTitle(Player player) {
        return Color.translate(TITLE);
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(player.getUniqueId());

        if (profile != null) {
            lines.add(Color.translate("&6Gold&7: &e" + profile.getGold()));
            lines.add(Color.translate("&5Ability&7: &d" + profile.getActiveAbility()));
            if (profile.getAbilityCooldown() != null && profile.getAbilityCooldown().active())
                lines.add(Color.translate("&f  > &7Cooldown: &c" + (profile.getAbilityCooldown().getTimeLeft() / 1000)));

            lines.add(Color.translate("&3Perk&7: &b" + profile.getActivePerk()));

        } else {
            lines.add(Color.translate("&cLoading Profile..."));
        }
        return lines;
    }


}
