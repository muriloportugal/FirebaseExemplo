package com.example.muriloportugal.firebaseexemplo.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.muriloportugal.firebaseexemplo.R;
import com.example.muriloportugal.firebaseexemplo.entidades.Produtos;

import java.util.ArrayList;

public class ProdutosAdapter extends ArrayAdapter<Produtos> {

    private ArrayList<Produtos> produto;
    private Context context;

    public ProdutosAdapter(Context c, ArrayList<Produtos> objects) {
        super(c,0, objects);
        this.produto = objects;
        this.context = c;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (produto!=null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_produtos,parent,false);

            TextView tvFiltroList = view.findViewById(R.id.tvFiltroList);
            TextView tvValorList = view.findViewById(R.id.tvValorList);

            Produtos produtos = produto.get(position);
            String filtroText = "Al. = "+produtos.getAltura()+", Larg. = "+produtos.getLargura()+
                    ", Peso = "+produtos.getPeso()+", Cor = "+produtos.getCor();
            tvFiltroList.setText(filtroText);
            String valorText = "Valor = "+ String.valueOf(produtos.getValor());
            tvValorList.setText(valorText);
        }

        return view;
    }
}
