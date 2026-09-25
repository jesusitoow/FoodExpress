package com.example.foodexpress.repositorio;

import com.example.foodexpress.modelo.Pedido;
import org.springframework.stereotype.Repository;
import java.util.concurrent.atomic.AtomicLong;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PedidoRepositoryMemoria implements PedidoRepository {
    private final Map<Long, Pedido> mapa = new ConcurrentHashMap<>();

    private final AtomicLong secuenciador = new AtomicLong(1);

    @Override
    public List<Pedido> buscarTodos() {
        return List.copyOf(mapa.values());
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return Optional.ofNullable(mapa.get(id));
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        if (pedido.getId() == null) {
            pedido.setId(secuenciador.getAndIncrement());
        }
        mapa.put(pedido.getId(), pedido);
        return pedido;
    }

    @Override
    public void eliminar(Long id) {
        mapa.remove(id);
    }
}
