package iesdomingoperezminik.es.donovanpcs.model;

import android.widget.ImageView;

public class PC {

    private int imageView;

    private String procesador;
    private String placaBase;
    private String ram;
    private String tarjetaGrafica;

    private int precio;

    public PC(int imageView, String procesador, String placaBase, String ram, String tarjetaGrafica, int precio) {
        this.imageView = imageView;
        this.procesador = procesador;
        this.placaBase = placaBase;
        this.ram = ram;
        this.tarjetaGrafica = tarjetaGrafica;
        this.precio = precio;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getProcesador() {
        return procesador;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public String getPlacaBase() {
        return placaBase;
    }

    public void setPlacaBase(String placaBase) {
        this.placaBase = placaBase;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getTarjetaGrafica() {
        return tarjetaGrafica;
    }

    public void setTarjetaGrafica(String tarjetaGrafica) {
        this.tarjetaGrafica = tarjetaGrafica;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return String.format("CPU: %s, Placa Base: %s, RAM: %s, Tarjeta gráfica: %s, Precio: %d", getProcesador(), getPlacaBase(), getRam(), getTarjetaGrafica(), getPrecio());
    }
}