package cc.pvpgames.ffa.ability.impl;

import cc.pvpgames.ffa.FFAPlugin;
import cc.pvpgames.ffa.ability.Ability;
import cc.pvpgames.ffa.profile.Profile;
import cc.pvpgames.ffa.utility.Timer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class RobinHood extends Ability implements Listener {

    public RobinHood() {
        super("RobinHood");
        setup();
    }

    public void setup() {
        setCost(350);
        setPermission("ffa.ability.robinhood");
        setCooldown(30);
        setDescription(Arrays.asList("&7Cost: &a" + getCost(), "&7Shooting a player will give them poison 2 for 4 seconds."));
        setDisplayName("&aRobin Hood");
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
            Arrow arrow = (Arrow) e.getDamager();

            if (!(arrow.getShooter() instanceof Player))
                return;

            Player shooter = (Player) arrow.getShooter();
            Player target = (Player) e.getEntity();

            Profile profile =  FFAPlugin.getInstance().getProfileManager().getProfile(shooter.getUniqueId());

            if (!profile.getAbility().equals(this))
                return;

            if (profile.getAbilityCooldown() != null && !profile.getAbilityCooldown().active())
                return;

            profile.setAbilityCooldown(new Timer(getCooldown()));
            target.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4, 1));
        }
    }

}
