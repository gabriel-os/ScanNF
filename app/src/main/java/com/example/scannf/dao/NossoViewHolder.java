package com.example.scannf.dao;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.scannf.R;

public class NossoViewHolder extends RecyclerView.ViewHolder {
    final TextView numNf;
    final TextView status;
    final TextView motivo;
    final TextView horario;
    final TextView fullName;
    final ImageButton btn_line;

    public NossoViewHolder(@NonNull View itemView) {
        super(itemView);
        numNf = itemView.findViewById(R.id.line_nf);
        status = itemView.findViewById(R.id.line_status);
        motivo = itemView.findViewById(R.id.line_reason);
        horario = itemView.findViewById(R.id.line_hour);
        btn_line = itemView.findViewById(R.id.btn_nf);
        fullName = itemView.findViewById(R.id.nf);
    }

}