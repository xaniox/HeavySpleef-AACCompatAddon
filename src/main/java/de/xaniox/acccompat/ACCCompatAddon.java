package de.xaniox.acccompat;

import de.xaniox.heavyspleef.addon.java.BasicAddOn;
import de.xaniox.heavyspleef.core.game.Game;
import de.xaniox.heavyspleef.core.game.GameManager;
import de.xaniox.heavyspleef.core.game.GameState;
import de.xaniox.heavyspleef.core.player.SpleefPlayer;
import me.konsolas.aac.api.HackType;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class ACCCompatAddon extends BasicAddOn implements Listener {

    @Override
    public void enable() {
        Bukkit.getPluginManager().registerEvents(this, getHeavySpleef().getPlugin());
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerViolation(PlayerViolationEvent event) {
        Player bukkitPlayer = event.getPlayer();
        SpleefPlayer player = getHeavySpleef().getSpleefPlayer(bukkitPlayer);
        GameManager gameManager = getHeavySpleef().getGameManager();

        Game game = gameManager.getGame(player);
        if (game == null || game.getGameState() != GameState.INGAME) {
            return;
        }

        HackType hackType = event.getHackType();
        if (hackType == HackType.FASTBREAK || hackType == HackType.NOSWING) {
            event.setCancelled(true);
        }
    }

}
