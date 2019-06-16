package com.example.scannf.dao;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scannf.R;
import com.example.scannf.activity.InfoNFActivity;

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
        NotaFiscal nota = nf.get(i);

        final NotaFiscal nt = nota;

        holder.fullName.setText(nota.getNumeroNota());
        holder.numNf.setText(shortNF(nota.getNumeroNota()));
        holder.status.setText(nota.getStatus());
        holder.motivo.setText(nota.getMotivo());
        holder.horario.setText(nota.getHorario());

        holder.btn_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = nt.getNumeroNota();
                String status = nt.getStatus();
                String motivo = nt.getMotivo();
                editNota(n, status, motivo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nf.size();
    }

    public void editNota(String num_nf, String status, String motivo) {
        Log.e("Batata", num_nf);
        Intent i = new Intent(context, InfoNFActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("edit", "true");
        i.putExtra("num_nf", num_nf);
        i.putExtra("status", status);
        i.putExtra("motivo", motivo);
        context.startActivity(i);
    }

    public String shortNF(String nf) {
        String nota;
        int serie, acessKey;
        serie = Integer.parseInt(nf.substring(22, 25));
        acessKey = Integer.parseInt(nf.substring(25, 34));

        nota = String.valueOf(acessKey) + " - " + String.valueOf(serie);
        return nota;
    }

}


