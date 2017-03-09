package br.com.soulskyye.tecnonutri.model;

import android.util.SparseArray;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class Item {

    public Item(){
        super();
        mealMap.put(0, "Café da Manhã");
        mealMap.put(1, "Lanche da Manhã");
        mealMap.put(2, "Almoço");
        mealMap.put(3, "Lanche da Tarde");
        mealMap.put(4, "Jantar");
        mealMap.put(5, "Ceia");
        mealMap.put(6, "Pré-Treino");
        mealMap.put(7, "Pós-Treino");
    }

    @SerializedName("feedHash")
    private String feedHash;
    @SerializedName("id")
    private long id;
    @SerializedName("profile")
    Profile profile;
    @SerializedName("meal")
    private int meal;
    @SerializedName("date")
    private String date;
    @SerializedName("image")
    private String image;
    @SerializedName("energy")
    private float energy;
    @SerializedName("carbohydrate")
    private float carbohydrate;
    @SerializedName("fat")
    private float fat;
    @SerializedName("protein")
    private float protein;
    @SerializedName("foods")
    private ArrayList<Food> foods;

    @Expose
    private SparseArray<String> mealMap = new SparseArray<>();
    @Expose
    private boolean liked = false;

    public String getMealName(){
        return this.mealMap.get(meal);
    }

    public long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public int getMeal() {
        return meal;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public float getEnergy() {
        return energy;
    }

    public SparseArray<String> getMealMap() {
        return mealMap;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getFeedHash() {
        return feedHash;
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

    public ArrayList<Food> getFoods() {
        return foods;
    }
}
