package ar.edu.unsam.pokedex.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stat_ {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("name")
    @Expose
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
