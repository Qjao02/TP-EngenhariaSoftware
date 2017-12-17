package com.ufsj.ies.controlfarma.model;

import java.util.Date;

/**
 * Created by JV on 16/12/2017.
 */

public class Registro{
    private Medicamento medicamento;
    private int numeroEstante;
    private int numeroRegistro;
    private int quantidade;
    private Date validade;
    private int codigoDeBarra;
    private String fabricante;
    private String fornecedor;

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public int getNumeroEstante() {
        return numeroEstante;
    }

    public void setNumeroEstante(int numeroEstante) {
        this.numeroEstante = numeroEstante;
    }

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public int getCodigoDeBarra() {
        return codigoDeBarra;
    }

    public void setCodigoDeBarra(int codigoDeBarra) {
        this.codigoDeBarra = codigoDeBarra;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }
}
