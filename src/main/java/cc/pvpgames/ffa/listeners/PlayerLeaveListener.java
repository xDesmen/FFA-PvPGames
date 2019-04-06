package cc.pvpgames.ffa.listeners;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.profile.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(e.getPlayer().getUniqueId());

        FFAPlugin.getInstance().getProfileManager().saveProfile(profile, true);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Profile profile = FFAPlugin.getInstance().getProfileManager().getProfile(e.getPlayer().getUniqueId());

        FFAPlugin.getInstance().getProfileManager().saveProfile(profile, true);
    }

}
