package cc.pvpgames.ffa.perk;

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

public class PerkInventory implements Listener {

    public Inventory perkInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 2, Color.translate("&dSelect Perk"));

        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(player.getUniqueId());

        FFAPlugin.getInstance().getPerkManager().getPerks().forEach(perk -> {

            boolean isActive = profile.getActivePerk().equalsIgnoreCase(perk.getDisplayName());

            ItemStack item = new ItemBuilder(perk.getDisplay(), Color.translate(perk.getDisplayName()), isActive,
                    "",
                    "&7Owned: " + (profile.hasPerk(perk) ? "&aTrue" : "&cFalse"),
                    "&7Cost: &6" + perk.getCost(),
                    "",
                    "&7" + perk.getDescription(),
                    profile.hasPerk(perk) && !isActive ? "&e&nClick to activate" : "&a&nClick to purchase").getItem();

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

        if (!e.getInventory().getTitle().contains("Select Perk"))
            return;
        if (e.getCurrentItem() == null)
            return;
        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if (item.getItemMeta() == null)
            return;

        Perk perk = FFAPlugin.getInstance().getPerkManager().getPerkByName(ChatColor.stripColor(item.getItemMeta().getDisplayName()));
        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(player.getUniqueId());

        if (perk == null)
            return;


        if (profile.hasPerk(perk)) {
            profile.setPerk(perk);
            player.sendMessage(Color.translate("&eChanged Perk to " + perk.getDisplayName()));
            player.closeInventory();
            return;
        }

        if (!profile.hasPerk(perk)) {
            int gold = profile.getGold();
            if (gold >= perk.getCost()) {
                FFAPlugin.getPermissions().playerAdd(player, perk.getPermission());
                profile.setGold(profile.getGold() - perk.getCost());
                player.sendMessage(Color.translate("&aYou have purchased the perk &e" + perk.getDisplayName() + " &afor &e" + perk.getCost()));
            } else {
                player.sendMessage(Color.translate("&cYou do not have enough gold to purchase the perk " + perk.getDisplayName()));
            }
        }


    }

}
