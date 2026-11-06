package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class UnidadeModel {
    @Expose
    private String nome;
    @Expose
    private String tpUnidade;
}
