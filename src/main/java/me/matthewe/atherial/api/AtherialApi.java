package me.matthewe.atherial.api;

/**
 * Created by Matthew E on 12/30/2017.
 */
public class AtherialApi {

    private static AtherialApi instance;

    public static AtherialApi getInstance() {
        if (instance == null) {
            instance = new AtherialApi();
        }
        return instance;
    }
    public AtherialApi() {
        instance = this;
    }
}
