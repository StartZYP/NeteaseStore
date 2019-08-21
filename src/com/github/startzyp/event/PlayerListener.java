package com.github.startzyp.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerListener implements Listener {
    @EventHandler
    public void PlayerJoinGame(PlayerJoinEvent event){
        UUID uniqueId = event.getPlayer().getUniqueId();

    }
}
