package cc.pvpgames.ffa.listeners;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.profile.Profile;
import cc.pvpgames.ffa.utility.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerKillListener implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent e) {

        Player killer = e.getEntity().getKiller();
        Player player = e.getEntity();

        Profile killerProfile = FFAPlugin.getInstance().getProfileManager().getProfile(killer.getUniqueId());
        Profile playerProfile = FFAPlugin.getInstance().getProfileManager().getProfile(player.getUniqueId());

        player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
        killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));

        killerProfile.setKills(killerProfile.getKills() + 1);
        playerProfile.setDeaths(playerProfile.getDeaths() + 1);

        e.setDeathMessage(Color.translate("&c" + player.getName() + " &6Has been killed by &c" + killer.getName()));

    }

}
