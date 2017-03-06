package br.com.soulskyye.tecnonutri.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ulissescurti on 3/6/17.
 */

public class Profile {

    @SerializedName("id")
    private long id;
    @SerializedName("image")
    private String photo;
    @SerializedName("name")
    private String name;
    @SerializedName("general_goal")
    private String goal;

}
