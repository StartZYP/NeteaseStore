package com.github.startzyp;

import com.github.startzyp.network.NeteaseNetWork;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class neteasestore extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getMessenger().registerIncomingPluginChannel(this,"storemod", new NeteaseNetWork ());
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "storemod");
        super.onEnable();
    }
}
