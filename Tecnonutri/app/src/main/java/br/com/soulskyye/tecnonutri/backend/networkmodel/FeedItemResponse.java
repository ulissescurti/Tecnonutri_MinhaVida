package br.com.soulskyye.tecnonutri.backend.networkmodel;

import com.google.gson.annotations.SerializedName;

import br.com.soulskyye.tecnonutri.model.Item;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class FeedItemResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("item")
    private Item item;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
