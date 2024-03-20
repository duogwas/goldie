package fithou.duogwas.goldie.Request;
//
// Created by duogwas on 18/03/2024.
//


public class PasswordDto {
    private String oldPass;

    private String newPass;

    public PasswordDto(String oldPass, String newPass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
}
