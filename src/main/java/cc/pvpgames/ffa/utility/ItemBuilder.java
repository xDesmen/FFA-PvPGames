package cc.pvpgames.ffa.utility;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private Material type;
    private String name;
    private String[] lore;

    private int amount;
    private short data = 0;
    private boolean glow;

    public ItemBuilder(Material type, String name, String... lore) {
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.amount = 1;
    }

    public ItemBuilder(Material type, String name, boolean glow, String... lore) {
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.glow = glow;
        this.amount = 1;
    }

    public ItemBuilder(Material type, String name, boolean glow, List<String> lore) {
        this.type = type;
        this.name = name;
        this.lore = lore.toArray(new String[0]);
        this.glow = glow;
        this.amount = 1;
    }

    public ItemBuilder(Material type, String name, int amount, String... lore) {
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.amount = amount;
    }

    public ItemBuilder(Material type, String name, int amount, short data, String... lore) {
        this.type = type;
        this.name = name;
        this.lore = lore;
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder(Material type, String name, int amount, short data, List<String> lore) {
        this.type = type;
        this.name = name;
        this.lore = lore.toArray(new String[0]);
        this.amount = amount;
        this.data = data;
    }

    public ItemBuilder(ItemStack display, String... lore) {
        this.type = display.getType();
        this.name = display.getItemMeta().getDisplayName();
        this.lore = lore;
        this.amount = display.getAmount();
        this.data = display.getDurability();
    }

    public ItemStack getItem() {
        List<String> lore = new ArrayList<>();

        Arrays.stream(this.lore).forEach(itemLore -> lore.add(ChatColor.translateAlternateColorCodes('&', itemLore)));

        ItemStack item = new ItemStack(this.type, this.amount, this.data);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.spigot().setUnbreakable(false);
        item.setItemMeta(meta);

        if (this.glow)
            EnchantGlow.addGlow(item);

        return item;
    }

}
