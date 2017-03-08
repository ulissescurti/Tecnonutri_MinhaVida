package br.com.soulskyye.tecnonutri.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.entity.Item;
import br.com.soulskyye.tecnonutri.entity.Profile;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class ProfileResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("t")
    private int t;
    @SerializedName("p")
    private int p;
    @SerializedName("profile")
    private Profile profile;
    @SerializedName("items")
    private ArrayList<Item> items;

    public boolean isSuccess() {
        return success;
    }

    public int getT() {
        return t;
    }

    public int getP() {
        return p;
    }

    public Profile getProfile() {
        return profile;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
