package com.ufsj.ies.controlfarma.model;

/**
 * Created by JV on 16/12/2017.
 */

public class ItemPedido{
    private float preco;
    private int quantidade;
    private Registro registro;

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
