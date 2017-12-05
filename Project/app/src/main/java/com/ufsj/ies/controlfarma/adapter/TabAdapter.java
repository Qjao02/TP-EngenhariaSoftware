package com.ufsj.ies.controlfarma.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ufsj.ies.controlfarma.fragment.CadastroClienteFragment;
import com.ufsj.ies.controlfarma.fragment.CadastroProdutoFragment;
import com.ufsj.ies.controlfarma.fragment.ClientesFragment;
import com.ufsj.ies.controlfarma.fragment.ProdutosFragment;

/**
 * Created by JV on 06/11/17.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    String[] tituloAbas = {"Cadastrar Cliente", "Clientes","Cadastrar Produto","Produtos"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch(position){
            case 0:
                fragment = new CadastroClienteFragment();
                break;
            case 1:
                fragment = new ClientesFragment();
                break;
            case 2:
                fragment = new CadastroProdutoFragment();
                break;
            case 3:
                fragment = new ProdutosFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position];
    }
}

