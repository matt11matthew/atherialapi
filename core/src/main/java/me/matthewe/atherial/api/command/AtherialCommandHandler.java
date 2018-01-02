package me.matthewe.atherial.api.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew E on 1/1/2018.
 */
public abstract class AtherialCommandHandler<C extends DefaultAtherialCommand> {
    protected final Map<String, C> commandMap;

    public AtherialCommandHandler() {
        this.commandMap = new HashMap<>();
    }

    public void registerCommand(C defaultAtherialCommand) {
        commandMap.put(defaultAtherialCommand.getAtherialCommand().name(), defaultAtherialCommand);
    }

    public abstract void registerCommands();

    /**
     * Getter for property 'commandMap'.
     *
     * @return Value for property 'commandMap'.
     */
    public Map<String, C> getCommandMap() {
        return commandMap;
    }
}
