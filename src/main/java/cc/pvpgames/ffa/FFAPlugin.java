package cc.pvpgames.ffa;

import cc.pvpgames.ffa.database.MySQLManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class FFAPlugin extends JavaPlugin {

    @Getter
    public static FFAPlugin instance;
    public MySQLManager mySQLManager;

    @Override
    public void onEnable() {
        instance = this;
        mySQLManager = new MySQLManager();
        mySQLManager.connect();
    }



}
