package fithou.duogwas.goldie.Model;
//
// Created by duogwas on 23/01/2024.
//


public class LoginDto {
    private String username;

    private String password;

    private String tokenFcm;

    public LoginDto(String username, String password, String tokenFcm) {
        this.username = username;
        this.password = password;
        this.tokenFcm = tokenFcm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTokenFcm() {
        return tokenFcm;
    }

    public void setTokenFcm(String tokenFcm) {
        this.tokenFcm = tokenFcm;
    }
}
