package cc.pvpgames.ffa.ability;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.profile.Profile;
import cc.pvpgames.ffa.utility.Color;
import cc.pvpgames.ffa.utility.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AbilityInventory implements Listener {

    public Inventory abilityInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 2, Color.translate("&dSelect Ability"));

        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(player.getUniqueId());

        FFAPlugin.getInstance().getAbilityManager().getAbilities().forEach(ability -> {

            boolean isActive = profile.getActiveAbility().equalsIgnoreCase(ability.getDisplayName());

            ItemStack item = new ItemBuilder(ability.getDisplay(), Color.translate(ability.getDisplayName()), isActive,
                    "",
                    "&7Owned: " + (profile.hasAbility(ability) ? "&aTrue" : "&cFalse"),
                    "&7Cost: &6" + ability.getCost(),
                    "",
                    "&7" + ability.getDescription(),
                    profile.hasAbility(ability) && !isActive ? "&e&nClick to activate" : "&a&nClick to purchase").getItem();

            inventory.addItem(item);
        });

        return inventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory() == null)
            return;
        if (!(e.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) e.getWhoClicked();

        if (!e.getInventory().getTitle().contains("Select Ability"))
            return;
        if (e.getCurrentItem() == null)
            return;
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if (item.getItemMeta() == null)
            return;

        Ability ability = FFAPlugin.getInstance().getAbilityManager().getAbilityByName(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(player.getUniqueId());

        if (ability == null)
            return;
        if (profile.hasAbility(ability)) {
            profile.setAbility(ability);
            player.sendMessage(Color.translate("&eChanged ability to " + ability.getDisplayName()));
            player.closeInventory();
            return;
        }

        if (!profile.hasAbility(ability)) {
            int gold = profile.getGold();
            if (gold >= ability.getCost()) {
                FFAPlugin.getPermissions().playerAdd(player, ability.getPermission());
                profile.setGold(profile.getGold() - ability.getCost());
                player.sendMessage(Color.translate("&aYou have purchased the ability &e" + ability.getDisplayName() + " &afor &e" + ability.getCost()));
            } else {
                player.sendMessage(Color.translate("&cYou do not have enough gold to purchase the ability " + ability.getDisplayName()));
            }
        }


    }

}
