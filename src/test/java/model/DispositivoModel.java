package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class DispositivoModel {
    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }

    public String getSetorUnidade() {
        return setorUnidade;
    }

    public void setSetorUnidade(String setorUnidade) {
        this.setorUnidade = setorUnidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Expose
    private String nome;
    @Expose
    private String dispositivo;
    @Expose
    private String status;
    @Expose
    private String nomeUnidade;
    @Expose
    private String setorUnidade;
}
