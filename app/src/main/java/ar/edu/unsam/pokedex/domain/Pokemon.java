package ar.edu.unsam.pokedex.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fedepujol on 13/11/17.
 */

public class Pokemon implements Serializable {
    private Integer id;
    private String name;
    private Integer weight;
    private Integer height;
    private List<Type> types = new ArrayList<Type>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }
}
