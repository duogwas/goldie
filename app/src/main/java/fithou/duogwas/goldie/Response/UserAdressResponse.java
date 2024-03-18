package fithou.duogwas.goldie.Response;
//
// Created by duogwas on 18/03/2024.
//


import fithou.duogwas.goldie.Entity.User;
import fithou.duogwas.goldie.Entity.Wards;

public class UserAdressResponse {
    private Long id;

    private String fullname;

    private String phone;

    private String streetName;

    private Boolean primaryAddres;

    private Wards wards;

    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
