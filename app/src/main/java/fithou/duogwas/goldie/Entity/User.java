package fithou.duogwas.goldie.Entity;
//
// Created by duogwas on 23/01/2024.
//


import java.io.Serializable;

public class User implements Serializable {
    int id;

    String username;

    String email;

    String password;

    String fullname;

    String phone;

    Boolean actived;

    String activation_key;

    String tokenFcm;

    Authority authorities;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullname='" + fullname + '\'' +
                ", phone='" + phone + '\'' +
                ", actived=" + actived +
                ", activation_key='" + activation_key + '\'' +
                ", tokenFcm='" + tokenFcm + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String email, String password, String fullname, String phone, String tokenFcm) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.tokenFcm = tokenFcm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getActived() {
        return actived;
    }

    public void setActived(Boolean actived) {
        this.actived = actived;
    }

    public String getActivation_key() {
        return activation_key;
    }

    public void setActivation_key(String activation_key) {
        this.activation_key = activation_key;
    }

    public String getTokenFcm() {
        return tokenFcm;
    }

    public void setTokenFcm(String tokenFcm) {
        this.tokenFcm = tokenFcm;
    }

    public Authority getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Authority authorities) {
        this.authorities = authorities;
    }
}
