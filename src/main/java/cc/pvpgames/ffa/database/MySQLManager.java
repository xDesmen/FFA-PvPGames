package cc.pvpgames.ffa.database;

import cc.pvpgames.ffa.FFAPlugin;
import com.zaxxer.hikari.HikariDataSource;

public class MySQLManager {

    public HikariDataSource hikari;

    public void connect() {
        String address = FFAPlugin.getInstance().getConfig().getString("database.address");
        String dbname = FFAPlugin.getInstance().getConfig().getString("database.db-name");
        String username = FFAPlugin.getInstance().getConfig().getString("database.username");
        String password = FFAPlugin.getInstance().getConfig().getString("database.password");

        hikari = new HikariDataSource();
        hikari.setMaximumPoolSize(10);
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", address);
        hikari.addDataSourceProperty("port", "3306");
        hikari.addDataSourceProperty("databaseName", dbname);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);
    }

}
