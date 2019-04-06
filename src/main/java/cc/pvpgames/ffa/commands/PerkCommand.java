package cc.pvpgames.ffa.commands;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.ability.AbilityInventory;
import cc.pvpgames.ffa.perk.PerkInventory;
import cc.pvpgames.ffa.utility.Color;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("perk")
public class PerkCommand extends BaseCommand {

    @Default
    public void onNormal(Player player) {
        player.openInventory(new PerkInventory().perkInventory(player));
    }

    @Subcommand("list")
    public void onList(Player player) {
        player.sendMessage(Color.translate("&aListing all Perks:"));
        FFAPlugin.getInstance().getPerkManager().getPerks().forEach(perk -> player.sendMessage(Color.translate("&7" + perk.getId() + " &f-> " + perk.getDisplayName())));
    }

}
