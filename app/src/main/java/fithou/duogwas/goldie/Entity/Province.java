package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 18/03/2024.
//


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Province implements Serializable {

    private Long id;

    private String name;

    private List<Districts> districts = new ArrayList<>();

    public Province(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Districts> getDistricts() {
        return districts;
    }

    public void setDistricts(List<Districts> districts) {
        this.districts = districts;
    }
}
