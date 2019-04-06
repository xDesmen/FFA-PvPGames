package cc.pvpgames.ffa.commands;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.ability.AbilityInventory;
import cc.pvpgames.ffa.utility.Color;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

@CommandAlias("ability")
public class AbilityCommand extends BaseCommand {

    @Default
    public void onNormal(Player player) {
        player.openInventory(new AbilityInventory().abilityInventory(player));
    }

    @Subcommand("list")
    public void onList(Player player) {
        player.sendMessage(Color.translate("&aListing all abilities:"));
        FFAPlugin.getInstance().getAbilityManager().getAbilities().forEach(ability -> player.sendMessage(Color.translate("&7" + ability.getId() + " &f-> " + ability.getDisplayName())));
    }

}
