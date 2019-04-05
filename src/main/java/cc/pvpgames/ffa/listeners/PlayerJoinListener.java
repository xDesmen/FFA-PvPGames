package cc.pvpgames.ffa.listeners;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.profile.Profile;
import cc.pvpgames.ffa.utility.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        e.setJoinMessage(null);

        Player player = e.getPlayer();

        FFAPlugin.getInstance().getConfig().getStringList("lang.join-message").forEach(str -> player.sendMessage(Color.translate(str)));

        player.sendMessage(Color.translate("&eGathering Profile..."));

        if (FFAPlugin.getInstance().getProfileManager().hasProfile(player.getUniqueId())) {
            FFAPlugin.getInstance().getProfileManager().loadProfile(player.getUniqueId());
        } else {
            FFAPlugin.getInstance().getProfileManager().createProfile(player.getUniqueId());
        }

        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(player.getUniqueId());
        if (profile == null) {
            player.kickPlayer(Color.translate("&cCorrupted Profile... Contact Staff"));
        }

    }


}
