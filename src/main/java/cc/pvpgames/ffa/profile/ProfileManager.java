package cc.pvpgames.ffa.profile;

import cc.pvpgames.ffa.FFAPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class ProfileManager {

    private Map<UUID, Profile> profiles = new HashMap<>();

    public Profile getProfile(UUID uuid) {
        return profiles.get(uuid);
    }

    public void createProfile(UUID uuid) {
        Profile profile = new Profile(uuid);
        profiles.put(uuid, profile);

        Bukkit.getScheduler().runTaskAsynchronously(FFAPlugin.getInstance(), () -> {
            try {
                Connection connection = FFAPlugin.getInstance().getMySQLManager().hikari.getConnection();
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users (uuid, gold, kills, deaths) VALUE (?,?,?,?)");
                statement.setString(1, uuid.toString());
                statement.setInt(2, 0);
                statement.setInt(3, 0);
                statement.setInt(4, 0);
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public void saveProfile(Profile profile, boolean async) {
        if (async) {
            Bukkit.getScheduler().runTaskAsynchronously(FFAPlugin.getInstance(), () -> {
                try {

                    Connection connection = FFAPlugin.getInstance().getMySQLManager().hikari.getConnection();
                    PreparedStatement statement = connection.prepareStatement("UPDATE users SET gold=?,kills=?,deaths=? WHERE UUID=?");
                    statement.setInt(1, profile.getGold());
                    statement.setInt(2, profile.getKills());
                    statement.setInt(3, profile.getDeaths());
                    statement.setString(4, profile.getUuid().toString());
                    statement.executeUpdate();
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            try {
                Connection connection = FFAPlugin.getInstance().getMySQLManager().hikari.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE users SET gold=?,kills=?,deaths=? WHERE UUID=?");
                statement.setInt(1, profile.getGold());
                statement.setInt(2, profile.getKills());
                statement.setInt(3, profile.getDeaths());
                statement.setString(4, profile.getUuid().toString());
                statement.executeUpdate();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void loadProfile(UUID uuid) {

        Bukkit.getScheduler().runTaskAsynchronously(FFAPlugin.getInstance(), () -> {
            try {
                Profile profile = new Profile(uuid);
                Connection connection = FFAPlugin.getInstance().getMySQLManager().hikari.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE uuid=?");
                statement.setString(1, uuid.toString());
                ResultSet results = statement.executeQuery();

                if (results.next()) {
                    profile.setKills(results.getInt("kills"));
                    profile.setDeaths(results.getInt("deaths"));
                    profile.setGold(results.getInt("gold"));
                    profiles.put(uuid, profile);
                    System.out.println("Done Loading");
                }
                statement.close();
                results.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public boolean hasProfile(UUID uuid) throws SQLException {
        PreparedStatement statement = null;
        ResultSet users = null;
        Connection connection = null;
        try {
            connection = FFAPlugin.getInstance().getMySQLManager().hikari.getConnection();
            statement = connection.prepareStatement("SELECT * FROM users WHERE uuid=?");
            statement.setString(1, uuid.toString());
            users = statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean profile = false;
        if (users != null) {
            profile = users.next();
            users.close();
            statement.close();
            connection.close();
        }
        return users != null && profile;
    }

    public void loadOnlineProfiles() {

        if (!(Bukkit.getOnlinePlayers().size() > 0))
            return;

        Bukkit.getOnlinePlayers().forEach(o -> {
            try {
                if (hasProfile(o.getUniqueId())) {
                    loadProfile(o.getUniqueId());
                } else {
                    createProfile(o.getUniqueId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void saveOnlineProfiles(boolean async) {
        getProfiles().values().forEach(profile -> saveProfile(profile, async));
    }


}
