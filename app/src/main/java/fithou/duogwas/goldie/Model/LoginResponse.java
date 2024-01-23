package fithou.duogwas.goldie.Model;
//
// Created by duogwas on 23/01/2024.
//


import java.util.List;

import fithou.duogwas.goldie.Model.Token;
import fithou.duogwas.goldie.Model.User;

public class LoginResponse {

    private Token token;
    private User user;

    public LoginResponse(Token token, User user) {
        this.token = token;
        this.user = user;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
