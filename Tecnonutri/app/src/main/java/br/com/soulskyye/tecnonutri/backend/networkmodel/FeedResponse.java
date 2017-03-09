package br.com.soulskyye.tecnonutri.backend.networkmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import br.com.soulskyye.tecnonutri.model.Item;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class FeedResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("t")
    private int t;
    @SerializedName("p")
    private int p;
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

    public ArrayList<Item> getItems() {
        return items;
    }
}
