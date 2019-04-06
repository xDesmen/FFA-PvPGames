package cc.pvpgames.ffa.listeners;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.profile.Profile;
import cc.pvpgames.ffa.utility.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import java.sql.SQLException;

@SuppressWarnings("deprecation")
public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPreJoin(PlayerPreLoginEvent e) throws SQLException {

        if (FFAPlugin.getInstance().getProfileManager().hasProfile(e.getUniqueId())) {
            FFAPlugin.getInstance().getProfileManager().loadProfile(e.getUniqueId());
        } else {
            FFAPlugin.getInstance().getProfileManager().createProfile(e.getUniqueId());
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);

/*        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(e.getPlayer().getUniqueId());
        if (profile == null) {
            e.getPlayer().kickPlayer(ChatColor.RED + "Corrupted Profile... Contact Staff.");
            return;
        }*/

        FFAPlugin.getInstance().getConfig().getStringList("lang.join-message").forEach(str -> e.getPlayer().sendMessage(Color.translate(str)));

    }


}
