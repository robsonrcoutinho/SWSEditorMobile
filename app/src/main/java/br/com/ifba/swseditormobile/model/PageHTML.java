package br.com.ifba.swseditormobile.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PageHTML extends RealmObject{
    @PrimaryKey
    private Long id;
    private String informacoesPagina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformacoesPagina() {
        return informacoesPagina;
    }

    public void setInformacoesPagina(String informacoesPagina) {
        this.informacoesPagina = informacoesPagina;
    }
}
