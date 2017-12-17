package com.ufsj.ies.controlfarma.model;

import java.util.ArrayList;

/**
 * Created by JV on 16/12/2017.
 */

public class Pedido {
    private int numeroPedido;
    private float valorTotal;
    private String descricao;
    private ArrayList<ItemPedido> itemPedidos;

    public ArrayList<ItemPedido> getItemPedidos() {
        return itemPedidos;
    }

    public void setItemPedidos(ArrayList<ItemPedido> itemPedidos) {
        this.itemPedidos = itemPedidos;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
