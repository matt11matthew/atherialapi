package me.matthewe.atherial.api.command;

/**
 * Created by Matthew E on 1/1/2018.
 */
public class DefaultAtherialCommand<S> {

    protected AtherialCommand atherialCommand;

    public DefaultAtherialCommand() {

    }

    /**
     * Setter for property 'atherialCommand'.
     *
     * @param atherialCommand Value to set for property 'atherialCommand'.
     */
    public DefaultAtherialCommand<S> setAtherialCommand(AtherialCommand atherialCommand) {
        this.atherialCommand = atherialCommand;
        return this;
    }

    public void execute(S sender, String[] args) {}

    public AtherialCommand getAtherialCommand() {
        return atherialCommand;
    }
}
