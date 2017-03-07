package br.com.soulskyye.tecnonutri.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ulissescurti on 3/7/17.
 */

public class Food {
    @SerializedName("id")
    private long id;
    @SerializedName("description")
    private String description;
    @SerializedName("amount")
    private String amount;
    @SerializedName("measure")
    private String measure;
    @SerializedName("weight")
    private String weight;
    @SerializedName("energy")
    private float energy;
    @SerializedName("carbohydrate")
    private float carbohydrate;
    @SerializedName("fat")
    private float fat;
    @SerializedName("protein")
    private float protein;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getMeasure() {
        return measure;
    }

    public String getWeight() {
        return weight;
    }

    public float getEnergy() {
        return energy;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public float getFat() {
        return fat;
    }

    public float getProtein() {
        return protein;
    }
}
