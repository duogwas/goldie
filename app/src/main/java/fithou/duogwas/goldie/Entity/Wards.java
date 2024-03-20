package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 18/03/2024.
//


public class Wards {
    private Long id;

    private String name;

    private Districts districts;

    public Wards(Long id) {
        this.id = id;
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

    public Districts getDistricts() {
        return districts;
    }

    public void setDistricts(Districts districts) {
        this.districts = districts;
    }
}
