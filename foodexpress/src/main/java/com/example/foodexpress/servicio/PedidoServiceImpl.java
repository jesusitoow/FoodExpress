package com.example.foodexpress.servicio;

import com.example.foodexpress.modelo.Pedido;
import com.example.foodexpress.repositorio.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repositorio;

    public PedidoServiceImpl(PedidoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Pedido> listarTodos() {
        return repositorio.buscarTodos();
    }

    @Override
    public Pedido obtenerPorId(Long id) {
        return repositorio.buscarPorId(id)
                .orElseThrow(() -> new NoSuchElementException("No existe el pedido con ID: " + id));
    }

    @Override
    public Pedido crear(String cliente, String plato, double precio) {
        if (cliente == null || cliente.isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        Pedido nuevo = new Pedido(null, cliente.trim(), plato.trim(), precio);
        return repositorio.guardar(nuevo);
    }

    @Override
    public void marcarEntregado(Long id) {
        Pedido pedido = obtenerPorId(id);
        pedido.setEntregado(true);
        repositorio.guardar(pedido);
    }

    @Override
    public void eliminar(Long id) {
        obtenerPorId(id);
        repositorio.eliminar(id);
    }
}
