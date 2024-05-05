package fithou.duogwas.goldie.Request;
//
// Created by duogwas on 20/03/2024.
//


import fithou.duogwas.goldie.Entity.Wards;

public class UserAdressRequest {
    private Long id;

    private String fullname;

    private String phone;

    private String streetName;

    private Boolean primaryAddres;

    private Wards wards;

    public UserAdressRequest(Long id, String fullname, String phone, String streetName, Boolean primaryAddres, Wards wards) {
        this.id = id;
        this.fullname = fullname;
        this.phone = phone;
        this.streetName = streetName;
        this.primaryAddres = primaryAddres;
        this.wards = wards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Boolean getPrimaryAddres() {
        return primaryAddres;
    }

    public void setPrimaryAddres(Boolean primaryAddres) {
        this.primaryAddres = primaryAddres;
    }

    public Wards getWards() {
        return wards;
    }

    public void setWards(Wards wards) {
        this.wards = wards;
    }
}
