package com.example.foodexpress.modelo;

public class Pedido {

    private Long id;
    private String cliente;
    private String plato;
    private double precio;
    private boolean entregado;

public Pedido(){}

public Pedido (Long id, String cliente, String plato, double precio){
    this.id = id;
    this.cliente = cliente;
    this.plato = plato;
    this.precio = precio;
    this.entregado = false;
}

public Long getId() { return id;}
public void setId (Long id) { this.id = id; }

public String getCliente() { return cliente; }
public void setCliente(String cliente) { this.cliente = cliente; }

public String getPlato() { return plato; }
public void setPlato(String plato) 
{ this.plato = plato; }

public double getPrecio() { return precio; }
public void setPrecio(double precio) 
{ this.precio = precio; }

public boolean isEntregado() { return entregado; }
public void setEntregado(boolean entregado) 
{ this.entregado = entregado; }
}
