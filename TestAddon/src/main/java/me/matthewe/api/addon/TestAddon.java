package me.matthewe.api.addon;

import me.matthewe.atherial.api.addon.AtherialAddon;
import me.matthewe.atherial.api.addon.AtherialAddonDescription;
import me.matthewe.atherial.api.addon.AtherialAddonManager;
import me.matthewe.atherial.api.addon.event.AtherialEvent;
import me.matthewe.atherial.api.addon.event.AtherialEventListener;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@PluginImplementation()

@AtherialAddonDescription(name = "Test", version = "1.0", authors = {"Matthew E"})
public class TestAddon extends AtherialAddon implements AtherialEventListener, Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        AtherialAddonManager manager = AtherialAddonManager.getManager();
        onAtherialJoin onAtherialJoin = new onAtherialJoin(event.getPlayer(), event.getJoinMessage());
        manager.callEvent(onAtherialJoin, this);
        event.setJoinMessage(onAtherialJoin.getJoinMessage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        AtherialAddonManager.getManager().registerListener(this, this);
        Bukkit.getPluginManager().registerEvents(this, Atherial);
    }

    public class onAtherialJoin extends AtherialEvent {
        private final Player player;
        private String joinMessage;

        public onAtherialJoin(Player player, String joinMessage) {
            this.player = player;
            this.joinMessage = joinMessage;
        }

        /**
         * Getter for property 'player'.
         *
         * @return Value for property 'player'.
         */
        public Player getPlayer() {
            return player;
        }

        /**
         * Getter for property 'joinMessage'.
         *
         * @return Value for property 'joinMessage'.
         */
        public String getJoinMessage() {
            return joinMessage;
        }

        /**
         * Setter for property 'joinMessage'.
         *
         * @param joinMessage Value to set for property 'joinMessage'.
         */
        public onAtherialJoin setJoinMessage(String joinMessage) {
            this.joinMessage = joinMessage;
            return this;
        }
    }


}
