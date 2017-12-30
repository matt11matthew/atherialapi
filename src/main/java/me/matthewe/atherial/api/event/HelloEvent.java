package me.matthewe.atherial.api.event;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class HelloEvent extends AtherialEvent implements Cancellable {
    private String message;
    private boolean isCancelled;

    public HelloEvent(String message) {
        this.message = message;
    }

    /**
     * Setter for property 'message'.
     *
     * @param message Value to set for property 'message'.
     */
    public HelloEvent setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Getter for property 'message'.
     *
     * @return Value for property 'message'.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }
}
