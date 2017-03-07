package br.com.soulskyye.tecnonutri.entity;

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
    private String carbohydrate;
    @SerializedName("fat")
    private String fat;
    @SerializedName("protein")
    private String protein;
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

    public String getCarbohydrate() {
        return carbohydrate;
    }

    public String getFat() {
        return fat;
    }

    public String getProtein() {
        return protein;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }
}
