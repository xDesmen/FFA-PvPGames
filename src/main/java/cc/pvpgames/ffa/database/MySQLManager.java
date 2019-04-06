package cc.pvpgames.ffa.database;

import cc.pvpgames.ffa.FFAPlugin;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.SQLException;

public class MySQLManager {

    public HikariDataSource hikari;

    public void connect() throws SQLException {
        String address = FFAPlugin.getInstance().getConfig().getString("database.address");
        String dbname = FFAPlugin.getInstance().getConfig().getString("database.db-name");
        String username = FFAPlugin.getInstance().getConfig().getString("database.username");
        String password = FFAPlugin.getInstance().getConfig().getString("database.password");

        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(20);
        hikari.setMinimumIdle(5);
        hikari.setIdleTimeout(10000);
        hikari.setMaxLifetime(10000);
        hikari.setConnectionTimeout(5000);
        hikari.setLeakDetectionThreshold(2000);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", address);
        hikari.addDataSourceProperty("port", "3306");
        hikari.addDataSourceProperty("databaseName", dbname);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);
        if (hikari.getConnection() != null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MySQL Connected!");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Error.");
        }
    }

}
