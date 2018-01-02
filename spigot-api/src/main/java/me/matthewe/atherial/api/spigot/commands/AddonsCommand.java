package me.matthewe.atherial.api.spigot.commands;

import me.matthewe.atherial.api.AtherialApi;
import me.matthewe.atherial.api.addon.AtherialAddon;
import me.matthewe.atherial.api.addon.AtherialAddonManager;
import me.matthewe.atherial.api.addon.event.AtherialEventListener;
import me.matthewe.atherial.api.command.AtherialCommand;
import me.matthewe.atherial.api.spigot.command.SpigotAtherialCommand;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Matthew E on 1/1/2018.
 */
@AtherialCommand(name = "addons", description = "The addons plugin", aliases = {"addon", "addonlist"})
public class AddonsCommand extends SpigotAtherialCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        TextComponent textComponent = new TextComponent();
        textComponent.addExtra(ChatColor.GREEN + "Addons: ");
        List<AtherialAddon> addons = AtherialApi.getInstance().getAddons();
        int index = 0;
        for (AtherialAddon atherialAddon : addons) {
            TextComponent addonTextComponent = new TextComponent();
            addonTextComponent.addExtra(ChatColor.WHITE + atherialAddon.getDescription().name());
            String authorsString = "";
            for (String s : atherialAddon.getDescription().authors()) {
                authorsString += s + ", ";
            }
            if (authorsString.endsWith(", ")) {
                authorsString = authorsString.substring(0, authorsString.length() - 2);
            }
            String listeners = "";
            List<Class<? extends AtherialEventListener>> handlers = AtherialAddonManager.getManager().getHandlers(atherialAddon);
            if (handlers != null) {
                listeners+="\n";
                for (Class<? extends AtherialEventListener> handler : handlers) {
                    listeners+=ChatColor.GRAY + " - " + ChatColor.GREEN + handler.getSimpleName()+"\n";
                }
                if (listeners.endsWith(", ")) {
                    listeners = listeners.substring(0, listeners.length() - 2);
                }
            } else {
                listeners = ChatColor.WHITE + "None";
            }
            addonTextComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{
                    new TextComponent(ChatColor.GREEN + "Name: " + ChatColor.WHITE + atherialAddon.getDescription().name()+"\n"),
                    new TextComponent(ChatColor.GREEN + "Version: " + ChatColor.WHITE + atherialAddon.getDescription().version()+"\n"),
                    new TextComponent(ChatColor.GREEN + "Authors: " + ChatColor.WHITE + authorsString+"\n"),
                    new TextComponent(ChatColor.GRAY +atherialAddon.getDescription().description()),
            }));
            textComponent.addExtra(addonTextComponent);
            index++;
            if (index < addons.size()) {
                textComponent.addExtra(ChatColor.GREEN + ", " + ChatColor.WHITE);

            }
        }
        if (sender instanceof Player) {
            ((Player) sender).spigot().sendMessage(textComponent);
        } else {
            sender.sendMessage(textComponent.getText());
        }
    }
}
