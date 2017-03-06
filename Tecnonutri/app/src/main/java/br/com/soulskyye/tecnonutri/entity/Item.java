package br.com.soulskyye.tecnonutri.entity;

import android.util.SparseArray;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @Expose
    private SparseArray<String> mealMap = new SparseArray<>();

    public String getMealName(){
        return this.mealMap.get(meal);
    }


}
