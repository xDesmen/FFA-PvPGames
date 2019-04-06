package cc.pvpgames.ffa.scoreboard;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.utility.Color;
import io.github.thatkawaiisam.nametags.BufferedNametag;
import io.github.thatkawaiisam.nametags.NametagHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NameTags {

    public void setup() {
        new NametagHandler(FFAPlugin.getInstance(),
                player -> {
                    List<BufferedNametag> tags = new ArrayList<>();
                    Bukkit.getOnlinePlayers().forEach(loopPlayer -> {
                        String colour = "&c";
                        tags.add(
                                //Buffered Nametag Object stores the target players name, the prefix, suffix, if you want health and the target players player object.
                                new BufferedNametag(
                                        loopPlayer.getName(),
                                        Color.translate(colour),
                                        "",
                                        true,
                                        loopPlayer
                                )
                        );
                    });
                    return tags;
                });
    }

}
