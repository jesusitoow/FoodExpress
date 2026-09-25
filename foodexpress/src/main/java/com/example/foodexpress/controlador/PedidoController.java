package com.example.foodexpress.controlador;

import com.example.foodexpress.modelo.Pedido;
import com.example.foodexpress.servicio.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService servicio;

    public PedidoController(PedidoService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Pedido> listar() {
        return servicio.listarTodos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPorId(@PathVariable Long id) {
        return servicio.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido crear(@RequestBody Pedido pedido) {
        return servicio.crear(
                pedido.getCliente(),
                pedido.getPlato(),
                pedido.getPrecio());
    }

    @PutMapping("/{id}/entregar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void marcarEntregado(@PathVariable Long id) {
        servicio.marcarEntregado(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarErrorValidacion(
            IllegalArgumentException e) {

        return Map.of("error", e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> manejarNoEncontrado(
            NoSuchElementException e) {

        return Map.of("error", e.getMessage());
    }
}

// Invoke-RestMethod -Uri "http://localhost:8080/api/pedidos" -Method Post
// -ContentType "application/json" -Body '{"cliente":"Carlos", "plato":"Pizza
// Pepperoni", "precio":12.50}'
// Invoke-RestMethod -Uri "http://localhost:8080/api/pedidos" -Method Post
// -ContentType "application/json" -Body '{"cliente":"Ana", "plato":"Hamburguesa
// Doble", "precio":9.80}'
// Invoke-RestMethod -Uri "http://localhost:8080/api/pedidos" -Method Post
// -ContentType "application/json" -Body '{"cliente":"Luis", "plato":"Tacos al
// Pastor", "precio":8.50}'