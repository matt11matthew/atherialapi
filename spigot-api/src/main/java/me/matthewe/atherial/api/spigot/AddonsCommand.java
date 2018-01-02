package me.matthewe.atherial.api.spigot;

import me.matthewe.atherial.api.command.AtherialCommand;
import me.matthewe.atherial.api.spigot.command.SpigotAtherialCommand;
import org.bukkit.command.CommandSender;

/**
 * Created by Matthew E on 1/1/2018.
 */
@AtherialCommand(name = "addons2")
public class AddonsCommand extends SpigotAtherialCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage("Hi");
    }
}
