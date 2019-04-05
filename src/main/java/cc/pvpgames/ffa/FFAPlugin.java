package cc.pvpgames.ffa;

import cc.pvpgames.ffa.database.MySQLManager;
import cc.pvpgames.ffa.listeners.PlayerJoinListener;
import cc.pvpgames.ffa.listeners.PlayerLeaveListener;
import cc.pvpgames.ffa.profile.ProfileManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class FFAPlugin extends JavaPlugin {

    @Getter
    public static FFAPlugin instance;
    private MySQLManager mySQLManager;
    private ProfileManager profileManager;

    @Override
    public void onEnable() {
        instance = this;
        mySQLManager = new MySQLManager();
        profileManager = new ProfileManager();
        mySQLManager.connect();
        registerEvents(new PlayerJoinListener(), new PlayerLeaveListener());
        profileManager.loadOnlineProfiles();
    }

    @Override
    public void onDisable() {
        profileManager.saveOnlineProfiles();
        instance = null;
        mySQLManager = null;
        profileManager = null;

    }

    public void registerEvents(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }



}
