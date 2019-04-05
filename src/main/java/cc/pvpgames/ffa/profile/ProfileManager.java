package cc.pvpgames.ffa.profile;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.utility.Color;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
                profile.getPlayer().sendMessage(Color.translate("&aProfile created."));
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

    }

    public void saveProfile(Profile profile) {
        Bukkit.getScheduler().runTaskAsynchronously(FFAPlugin.getInstance(), () -> {

            try {
                Connection connection = FFAPlugin.getInstance().getMySQLManager().hikari.getConnection();
                PreparedStatement statement = connection.prepareStatement("UPDATE users SET gold=?,kills=?,deaths=? WHERE UUID=?");
                statement.setInt(1, profile.getGold());
                statement.setInt(2, profile.getKills());
                statement.setInt(3, profile.getDeaths());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
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
                }

                profiles.put(uuid, profile);
                profile.getPlayer().sendMessage(Color.translate("&aProfile loaded."));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    public boolean hasProfile(UUID uuid) throws SQLException {

        Connection connection = FFAPlugin.getInstance().getMySQLManager().hikari.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE uuid=?");
        statement.setString(1, uuid.toString());
        ResultSet users = statement.executeQuery();

        return users.next();
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

    public void saveOnlineProfiles() {
        getProfiles().values().forEach(this::saveProfile);
    }


}
