package me.matthewe.atherial.api.addon;

/**
 * Created by Matthew E on 12/30/2017.
 */
public abstract class AtherialAddon {
    private AtherialAddonDescription description;

    public void onLoad() {}
    public void onUnload() {}

    public AtherialAddonManager getAddonManager() {
        return AtherialAddonManager.getInstance();
    }


    /**
     * Getter for property 'description'.
     *
     * @return Value for property 'description'.
     */
    public AtherialAddonDescription getDescription() {
        return description;
    }

    protected void setDescription(AtherialAddonDescription description) {
        this.description = description;
    }
}
