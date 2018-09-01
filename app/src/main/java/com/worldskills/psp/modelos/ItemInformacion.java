package com.worldskills.psp.modelos;

public class ItemInformacion {

    private double porcentaje;
    private String fase;
    private String cantidad;

    public ItemInformacion(double porcentaje, String fase, String cantidad) {
        this.porcentaje = porcentaje;
        this.fase = fase;
        this.cantidad = cantidad;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
