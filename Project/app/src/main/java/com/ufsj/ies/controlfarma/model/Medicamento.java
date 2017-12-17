package com.ufsj.ies.controlfarma.model;

/**
 * Created by JV on 05/12/2017.
 */

public class Medicamento{
    private String nome;
    private String principioAtivo;
    private String especificacoesArmazenamento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public String getEspecificacoesArmazenamento() {
        return especificacoesArmazenamento;
    }

    public void setEspecificacoesArmazenamento(String especificacoesArmazenamento) {
        this.especificacoesArmazenamento = especificacoesArmazenamento;
    }
}
