# Servicio de Pedidos

## Descripción

La capa de servicio contiene la lógica de negocio de la aplicación **FoodExpress**.

Su función principal es actuar como intermediario entre el controlador y el repositorio. De esta manera, el controlador se encarga de recibir las peticiones HTTP, mientras que el servicio decide qué operación debe realizarse y aplica las reglas correspondientes.

La capa está compuesta por:

```text
servicio
│
├── PedidoService
└── PedidoServiceImpl
```

---

# PedidoService

`PedidoService` es una interfaz que define las operaciones que puede realizar el sistema sobre los pedidos.

```java
public interface PedidoService {

    List<Pedido> listarTodos();

    Pedido obtenerPorId(Long id);

    Pedido crear(String cliente, String plato, double precio);

    void marcarEntregado(Long id);

    void eliminar(Long id);
}
```

La interfaz permite separar la definición de las operaciones de su implementación.

---

# PedidoServiceImpl

`PedidoServiceImpl` es la implementación de `PedidoService`.

Utiliza la anotación:

```java
@Service
```

Esto permite que Spring registre la clase como un componente de la aplicación y pueda inyectarla donde sea necesaria.

```java
@Service
public class PedidoServiceImpl implements PedidoService {
```

El servicio recibe una instancia de `PedidoRepository` mediante inyección de dependencias por constructor:

```java
private final PedidoRepository repositorio;

public PedidoServiceImpl(PedidoRepository repositorio) {
    this.repositorio = repositorio;
}
```

La comunicación entre las capas queda de la siguiente manera:

```text
PedidoController
       ↓
PedidoService
       ↓
PedidoRepository
       ↓
PedidoRepositoryMemoria
```

---

# Operaciones del Servicio

## 1. Listar todos los pedidos

### Método

```java
List<Pedido> listarTodos()
```

### Función

Obtiene todos los pedidos registrados.

El servicio delega la búsqueda al repositorio:

```java
@Override
public List<Pedido> listarTodos() {
    return repositorio.buscarTodos();
}
```

### Flujo

```text
Cliente
   ↓
GET /api/pedidos
   ↓
PedidoController
   ↓
PedidoService
   ↓
PedidoRepository
   ↓
Lista de pedidos
```

---

# 2. Obtener pedido por ID

### Método

```java
Pedido obtenerPorId(Long id)
```

### Función

Busca un pedido utilizando su identificador.

```java
@Override
public Pedido obtenerPorId(Long id) {
    return repositorio.buscarPorId(id)
            .orElseThrow(() ->
                new NoSuchElementException(
                    "No existe el pedido con ID: " + id
                )
            );
}
```

El repositorio devuelve un `Optional<Pedido>`.

Si el pedido existe, se devuelve.

Si no existe, se lanza una `NoSuchElementException`.

Esta excepción posteriormente es manejada por el controlador y convertida en una respuesta HTTP:

```text
404 Not Found
```

---

# 3. Crear un pedido

### Método

```java
Pedido crear(String cliente, String plato, double precio)
```

### Función

Crea un nuevo pedido después de comprobar que los datos básicos sean válidos.

Primero se valida el cliente:

```java
if (cliente == null || cliente.isBlank()) {
    throw new IllegalArgumentException(
        "El nombre del cliente es obligatorio"
    );
}
```

Después se valida el plato:

```java
if (plato == null || plato.isBlank()) {
    throw new IllegalArgumentException(
        "El plato es obligatorio"
    );
}
```

Finalmente se valida el precio:

```java
if (precio <= 0) {
    throw new IllegalArgumentException(
        "El precio debe ser mayor a 0"
    );
}
```

Después de superar las validaciones se crea el objeto:

```java
Pedido nuevo = new Pedido(
    null,
    cliente.trim(),
    plato.trim(),
    precio
);
```

El ID inicialmente es `null` porque será asignado por el repositorio.

Finalmente se guarda:

```java
return repositorio.guardar(nuevo);
```

### Flujo

```text
POST /api/pedidos
        ↓
PedidoController
        ↓
PedidoService
        ↓
Validaciones
        ↓
Crear Pedido
        ↓
PedidoRepository
        ↓
Pedido creado
```

---

# 4. Marcar pedido como entregado

### Método

```java
void marcarEntregado(Long id)
```

### Función

Cambia el estado del pedido para indicar que ya fue entregado.

Primero se obtiene el pedido:

```java
Pedido pedido = obtenerPorId(id);
```

Esto también permite comprobar que el pedido exista.

Después se modifica su estado:

```java
pedido.setEntregado(true);
```

Finalmente se guarda nuevamente:

```java
repositorio.guardar(pedido);
```

### Endpoint relacionado

```http
PUT /api/pedidos/{id}/entregar
```

### Flujo

```text
PUT /api/pedidos/1/entregar
        ↓
PedidoController
        ↓
PedidoService
        ↓
Buscar pedido
        ↓
Cambiar entregado = true
        ↓
Guardar pedido
```

---

# 5. Eliminar pedido

### Método

```java
void eliminar(Long id)
```

### Función

Elimina un pedido existente.

Antes de eliminarlo se comprueba que exista:

```java
obtenerPorId(id);
```

Después se solicita al repositorio que lo elimine:

```java
repositorio.eliminar(id);
```

### Flujo

```text
DELETE /api/pedidos/1
        ↓
PedidoController
        ↓
PedidoService
        ↓
Comprobar existencia
        ↓
PedidoRepository
        ↓
Pedido eliminado
```

---

# Manejo de Validaciones

Las validaciones pertenecen a la capa de servicio porque representan reglas de negocio.

Por ejemplo:

```java
if (precio <= 0) {
    throw new IllegalArgumentException(
        "El precio debe ser mayor a 0"
    );
}
```

El servicio no se encarga de decidir directamente qué código HTTP devolver.

En su lugar, lanza la excepción y el controlador la transforma en:

```http
400 Bad Request
```

Esto mantiene separadas las responsabilidades.

---

# Responsabilidades del Servicio

La capa de servicio se encarga de:

* Aplicar las reglas de negocio.
* Validar los datos recibidos.
* Crear pedidos.
* Consultar pedidos.
* Buscar pedidos por ID.
* Cambiar el estado de entrega.
* Solicitar la eliminación de pedidos.
* Comunicarse con el repositorio.

La capa de servicio **no se encarga de manejar directamente las peticiones HTTP**.

Por ejemplo, el servicio no utiliza:

```java
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
```

Estas responsabilidades pertenecen al controlador.

---

# Principio de Separación de Responsabilidades

FoodExpress separa las responsabilidades de cada capa:

```text
┌─────────────────────────────┐
│       CONTROLADOR           │
│                             │
│ Recibe peticiones HTTP      │
│ Devuelve respuestas HTTP    │
└──────────────┬──────────────┘
               │
               ↓
┌─────────────────────────────┐
│          SERVICIO           │
│                             │
│ Lógica de negocio           │
│ Validaciones                │
│ Operaciones sobre pedidos   │
└──────────────┬──────────────┘
               │
               ↓
┌─────────────────────────────┐
│        REPOSITORIO          │
│                             │
│ Almacenamiento de datos     │
└─────────────────────────────┘
```

Esta separación permite que cada componente tenga una responsabilidad concreta y facilita la comprensión y mantenimiento del proyecto.

---

# Resumen

`PedidoService` define las operaciones disponibles para gestionar pedidos, mientras que `PedidoServiceImpl` contiene la lógica necesaria para ejecutarlas.

La capa de servicio funciona como el punto intermedio entre el controlador y el repositorio:

```text
Controller → Service → Repository
```

De esta manera, el controlador no necesita conocer cómo se almacenan los pedidos y el repositorio no necesita conocer cómo se reciben las peticiones HTTP.
