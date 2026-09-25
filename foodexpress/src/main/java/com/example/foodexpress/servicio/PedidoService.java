package com.example.foodexpress.servicio;

import com.example.foodexpress.modelo.Pedido;
import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos();

    Pedido obtenerPorId(Long id);

    Pedido crear(String cliente, String plato, double precio);

    void marcarEntregado(Long id);

    void eliminar(Long id);
}
