package com.github.startzyp.Task;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.startzyp.entity.GoodEntity;
import com.github.startzyp.neteasestore;
import com.github.startzyp.util.Encypt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SendGood implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            UUID uniqueId = player.getUniqueId();
            if (!neteasestore.PlayerGoodInfo.containsKey(uniqueId)){
                player.sendMessage("你没买东西啊");
                return false;
            }
            StringBuilder orderlist = new StringBuilder();
            List<GoodEntity> goodEntities = neteasestore.PlayerGoodInfo.get(uniqueId);
            for(GoodEntity tempgood:goodEntities){
                String cmd = tempgood.getCmd();
                String orderid = tempgood.getOrderid();
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),cmd.replace("{player}",player.getName()));
                orderlist.append(orderid).append(",");
            }
            String json = "{\"gameid\": \""+neteasestore.GameId+"\",\"uuid\": \""+uniqueId+"\",\"orderid_list\": ["+orderlist.substring(0,orderlist.length()-1)+"]}";
            System.out.println(json);
            String KeyCode = "";
            try{
                KeyCode = Encypt.HMACSHA256("POST/ship-mc-item-order"+json,neteasestore.SecretKey);
                System.out.println("Encrypt:"+KeyCode);
            }catch (Exception e){
                System.out.println("加密错误");
            }
            Map<String,String> Header = new HashMap<>();
            Header.put("content-type","application/json; charset=utf-8");
            Header.put("netease-server-sign", KeyCode);
            Header.put("cache-control","no-cache");
            String body = HttpRequest.post(neteasestore.SendGoodUrl).headers(Header).send(json).body();
            try{
                body = URLDecoder.decode(body,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println(body);
            neteasestore.PlayerGoodInfo.remove(uniqueId);
        }

        return false;
    }
}