package com.worldskills.psp.modelos;

public class ItemProyecto {

    private int idProyecto;
    private String nombreProyecto;
    private int tiempoTotal;

    /*Constructor que inicializa el objeto itemProyecto con sus atributos*/
    public ItemProyecto(int idProyecto, String nombreProyecto, int tiempoTotal) {
        this.idProyecto = idProyecto;
        this.nombreProyecto = nombreProyecto;
        this.tiempoTotal = tiempoTotal;
    }

    /*medoto para traer el valor de la variable idProyecto*/
    public int getIdProyecto() {
        return idProyecto;
    }
    /*medoto para enviar el valor de la variable idProyecto*/
    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }
    /*medoto para traer el valor de la variable nombreProyecto*/
    public String getNombreProyecto() {
        return nombreProyecto;
    }
    /*medoto para enviar el valor de la variable nombreProyecto*/
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }
    /*medoto para traer el valor de la variable tiempoTotal*/
    public int getTiempoTotal() {
        return tiempoTotal;
    }
    /*medoto para enviar el valor de la variable tiempoTotal*/
    public void setTiempoTotal(int tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }
}
