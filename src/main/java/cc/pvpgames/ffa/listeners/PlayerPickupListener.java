package cc.pvpgames.ffa.listeners;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.profile.Profile;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPickupListener implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        if (e.getItem().getItemStack().getType() == Material.GOLD_INGOT) {
            e.setCancelled(true);
            Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(e.getPlayer().getUniqueId());
            ItemStack item = e.getItem().getItemStack();
            int goldAmount = profile.getPerk() != null && profile.getPerk().getId().equalsIgnoreCase("Multiply") ? item.getAmount()*2 : item.getAmount();

            profile.setGold(profile.getGold() + goldAmount);

            e.getItem().remove();

        }
    }

}
