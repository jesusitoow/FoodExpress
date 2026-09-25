package com.example.foodexpress.repositorio;

import com.example.foodexpress.modelo.Pedido;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository {

    List<Pedido> buscarTodos();

    Optional<Pedido> buscarPorId(Long id);

    Pedido guardar(Pedido pedido);

    void eliminar(Long id);
}