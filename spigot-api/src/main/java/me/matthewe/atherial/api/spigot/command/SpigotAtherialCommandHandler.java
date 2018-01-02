package me.matthewe.atherial.api.spigot.command;

import me.matthewe.atherial.api.command.AtherialCommandHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by Matthew E on 1/1/2018.
 */
public class SpigotAtherialCommandHandler extends AtherialCommandHandler<SpigotAtherialCommand> {

    @Override
    public void registerCommands() {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap spigotCommandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.forEach((s, spigotAtherialCommand) -> {
                ReflectCommand reflectCommand = new ReflectCommand(spigotAtherialCommand.getAtherialCommand().name());
                reflectCommand.setAliases(Arrays.asList(spigotAtherialCommand.getAtherialCommand().aliases()));
                reflectCommand.setDescription(spigotAtherialCommand.getAtherialCommand().description());
                reflectCommand.setUsage(spigotAtherialCommand.getAtherialCommand().usage());
                reflectCommand.setExecutor(spigotAtherialCommand);
                spigotCommandMap.register(reflectCommand.exe.getAtherialCommand().name(), reflectCommand);

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final class ReflectCommand extends Command {
        private SpigotAtherialCommand exe = null;

        public ReflectCommand(String command) {
            super(command);
        }

        public void setExecutor(SpigotAtherialCommand exe) {
            this.exe = exe;
        }

        @Override
        public boolean execute(CommandSender sender, String commandLabel, String[] args) {
            return exe != null && exe.onCommand(sender, this, commandLabel, args);
        }
    }
}
