package com.example.scannf.dao;

public class NotaFiscal {

    private final String numeroNota;
    private final String status;
    private final String motivo;
    private final String horario;


    public NotaFiscal(String numeroNota, String status,
                      String motivo, String horario) {

        this.numeroNota = numeroNota;
        this.status = status;
        this.motivo = motivo;
        this.horario = horario;
    }

    public NotaFiscal(String numeroNota, String nomeAutor, String status, String motivo, String horario) {
        this.status = status;
        this.numeroNota = numeroNota;
        this.motivo = motivo;
        this.horario = horario;
    }

    public String getNumeroNota() {
        return numeroNota;
    }

    public String getStatus() {
        return status;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getHorario() {
        return horario;
    }
}