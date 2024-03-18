package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 18/03/2024.
//


import java.util.ArrayList;
import java.util.List;

public class Districts {
    private Long id;

    private String name;

    private Province province;

    private List<Wards> wards = new ArrayList<>();

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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public List<Wards> getWards() {
        return wards;
    }

    public void setWards(List<Wards> wards) {
        this.wards = wards;
    }
}
