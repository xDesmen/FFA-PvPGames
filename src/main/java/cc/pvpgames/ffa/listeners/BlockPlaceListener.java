package cc.pvpgames.ffa.listeners;

import cc.pvpgames.ffa.FFAPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.block.*;

public class BlockPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlace(BlockPlaceEvent e) {

        if (e.isCancelled())
            return;
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        if (e.getBlockReplacedState().getBlock() == null || e.getBlockReplacedState().getBlock().getType() == Material.AIR)
            return;

        Block block = e.getBlockPlaced();

        Bukkit.getScheduler().runTaskLater(FFAPlugin.getInstance(), () -> block.setType(Material.AIR), 60);

    }

}
