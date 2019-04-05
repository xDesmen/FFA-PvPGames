package cc.pvpgames.ffa.profile;

import cc.pvpgames.ffa.FFAPlugin;
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
                PreparedStatement statement = connection.prepareStatement("INSERT INTO users (uuid, gold, kills, deaths)");
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });

    }

    public void saveProfile(Profile profile) {

    }

    public boolean hasProfile(UUID uuid) throws SQLException {

        Connection connection = FFAPlugin.getInstance().getMySQLManager().hikari.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE uuid=?");
        statement.setString(0, uuid.toString());
        ResultSet users = statement.executeQuery();

        return users.next();
    }

}
