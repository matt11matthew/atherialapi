package me.matthewe.atherial.api.command;

/**
 * Created by Matthew E on 1/1/2018.
 */
public class DefaultAtherialCommand<S> {
    protected AtherialCommand atherialCommand;

    public DefaultAtherialCommand() {
        Class<? extends DefaultAtherialCommand> aClass = getClass();
        if (aClass.isAnnotationPresent(AtherialCommand.class)) {
            atherialCommand = aClass.getAnnotation(AtherialCommand.class);
        }
    }

    /**
     * Getter for property 'atherialCommand'.
     *
     * @return Value for property 'atherialCommand'.
     */
    public AtherialCommand getAtherialCommand() {
        return atherialCommand;
    }

    public void execute(S sender, String[] args) {}
}
