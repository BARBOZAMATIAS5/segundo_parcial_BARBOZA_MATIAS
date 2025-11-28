/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author gabri
 */
public class CarritoItem implements Serializable{
    private Producto producto;
    private int cantidad;
    
    public CarritoItem(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public double calcularSubtotal(){
        return cantidad * this.producto.getPrecio();
    }
    
    public String stringFormateado(int e){
        return this.producto.getNombre() + formatearCantidad(e);
    }
    
    private String formatearCantidad(int e){
        return " ------ " + e;
    }
    
    @Override
    public String toString(){
        return this.producto.getNombre() + " - " + this.cantidad + " - " + subtotal();
    }
    
    public double subtotal(){
        return this.cantidad * this.producto.getPrecio();
    }
}
