package me.matthewe.atherial.api;

import me.matthewe.atherial.api.addon.AtherialAddonManager;
import me.matthewe.atherial.api.event.AtherialEventHandler;
import me.matthewe.atherial.api.event.HelloEvent;

import java.io.File;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class Main   {
    public static void main(String[] args) {
        AtherialAddonManager.getManager().loadAddons(new File("C:/Users/matt1/Documents/GitHub/AtherialAntiVPNProject/atherialspigotapi/src/main/resources/addons/"));

    }

    public static class HelloEventHandler implements AtherialEventListener {
        @AtherialEventHandler
        public void onHelloEvent(HelloEvent event) {
            event.setMessage("hi");
            event.setCancelled(true);
        }
    }


}
