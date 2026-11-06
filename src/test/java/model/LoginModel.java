package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class LoginModel {

    @Expose
    private String email;
    @Expose
    private String senha;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
