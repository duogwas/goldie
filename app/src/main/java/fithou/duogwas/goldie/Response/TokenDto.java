package fithou.duogwas.goldie.Response;
//
// Created by duogwas on 23/01/2024.
//


import fithou.duogwas.goldie.Entity.User;

public class TokenDto {
    private String token;

    private User user;

    public TokenDto(String token, User user) {
        this.token = token;
        this.user = user;
    }

    @Override
    public String toString() {
        return "TokenDto{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
