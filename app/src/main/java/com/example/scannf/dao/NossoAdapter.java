package com.example.scannf.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scannf.R;

import java.util.List;

public class NossoAdapter extends RecyclerView.Adapter {
    private List<NotaFiscal> nf;
    private Context context;


    public NossoAdapter(List<NotaFiscal> nf, Context context) {
        this.nf = nf;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_line_view, parent, false);
        NossoViewHolder holder = new NossoViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        NossoViewHolder holder = (NossoViewHolder) viewHolder;
        final String teste;
        NotaFiscal nota = nf.get(i);

        holder.numNf.setText(nota.getNumeroNota());
        holder.status.setText(nota.getStatus());
        holder.motivo.setText(nota.getMotivo());
        holder.horario.setText(nota.getHorario());
        teste = (String) holder.horario.getText();
        holder.btn_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TESTE", "FOOOOOOOOOOOOOOOOOI" + teste);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nf.size();
    }

}


