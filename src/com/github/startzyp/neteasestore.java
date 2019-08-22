package com.github.startzyp;

import com.github.startzyp.Task.SendGood;
import com.github.startzyp.entity.GoodEntity;
import com.github.startzyp.event.PlayerListener;
import com.github.startzyp.network.NeteaseNetWork;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class neteasestore extends JavaPlugin {
    public static String GameId;
    public static String SecretKey;
    public static String GetGoodsUrl;
    public static String SendGoodUrl;
    public static Map<UUID, List<GoodEntity>> PlayerGoodInfo = new HashMap<>();
    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(),"config.yml");
        if (!file.exists()){
            saveDefaultConfig();
        }
        GameId = getConfig().getString("GameId");
        SecretKey = getConfig().getString("SecretKey");
        GetGoodsUrl = getConfig().getString("GetGoodsUrl");
        SendGoodUrl = getConfig().getString("SendGoodUrl");
        System.out.println("GameId:"+GameId);
        System.out.println("SecretKey:"+SecretKey);
        System.out.println("GetGoodsUrl:"+GetGoodsUrl);
        System.out.println("SendGoodUrl:"+SendGoodUrl);
        getServer().getMessenger().registerIncomingPluginChannel(this,"storemod", new NeteaseNetWork ());
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "storemod");
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        Bukkit.getPluginCommand("get").setExecutor(new SendGood());
        super.onEnable();
    }
}
