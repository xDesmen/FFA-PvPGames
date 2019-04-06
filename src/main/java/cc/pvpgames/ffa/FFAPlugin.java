package cc.pvpgames.ffa;

import cc.pvpgames.ffa.ability.AbilityInventory;
import cc.pvpgames.ffa.ability.AbilityManager;
import cc.pvpgames.ffa.commands.AbilityCommand;
import cc.pvpgames.ffa.commands.PerkCommand;
import cc.pvpgames.ffa.database.MySQLManager;
import cc.pvpgames.ffa.listeners.*;
import cc.pvpgames.ffa.perk.PerkInventory;
import cc.pvpgames.ffa.perk.PerkManager;
import cc.pvpgames.ffa.profile.ProfileManager;
import cc.pvpgames.ffa.scoreboard.NameTags;
import cc.pvpgames.ffa.scoreboard.Scoreboard;
import co.aikar.commands.BukkitCommandManager;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import net.milkbowl.vault.permission.Permission;
import java.sql.SQLException;
import java.util.Arrays;

@Getter
public class FFAPlugin extends JavaPlugin {

    @Getter
    public static FFAPlugin instance;
    private MySQLManager mySQLManager;
    private ProfileManager profileManager;
    private AbilityManager abilityManager;
    private PerkManager perkManager;

    @Override
    public void onEnable() {
        instance = this;
        createConfig();
        mySQLManager = new MySQLManager();
        profileManager = new ProfileManager();
        abilityManager = new AbilityManager();
        perkManager = new PerkManager();

        try {
            mySQLManager.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        registerCommands(new BukkitCommandManager(this));
        registerEvents(new PlayerJoinListener(), new PlayerLeaveListener(), new BlockPlaceListener(), new PlayerKillListener(), new PlayerPickupListener(), new AbilityInventory(), new PerkInventory());
        setupPermissions();

        profileManager.loadOnlineProfiles();
        setupScoreboard();
    }

    @Override
    public void onDisable() {
        profileManager.saveOnlineProfiles(false);
        instance = null;
        mySQLManager = null;
        profileManager = null;
    }

    private void registerCommands(BukkitCommandManager commandManager) {

        Arrays.asList(new AbilityCommand(), new PerkCommand())
                .forEach(commandManager::registerCommand);
    }

    private void registerEvents(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void setupScoreboard() {
        //Start Instance
        NameTags nameTags = new NameTags();
        Assemble assemble = new Assemble(this, new Scoreboard());

        //Set Interval (Tip: 20 ticks = 1 second)
        assemble.setTicks(2);

        //Set Style (Tip: Viper Style starts at -1 and goes down)
        assemble.setAssembleStyle(AssembleStyle.KOHI);
        nameTags.setup();
    }

    private static Permission perms = null;
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
    public static Permission getPermissions() {
        return perms;
    }
}
