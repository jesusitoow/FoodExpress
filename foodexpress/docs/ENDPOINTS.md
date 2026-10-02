# ENDPOINTS API - FoodExpress

## Descripción

FoodExpress es una API REST desarrollada con Spring Boot para administrar pedidos de una tienda de domicilios.

La API permite:

* Registrar pedidos.
* Consultar pedidos.
* Buscar pedidos por identificador.
* Marcar pedidos como entregados.
* Eliminar pedidos.

Base URL:

```text
http://localhost:8080/api/pedidos
```

---

# 1. Listar todos los pedidos

Obtiene la lista completa de pedidos registrados.

### Endpoint

```http
GET /api/pedidos
```

### Respuesta Exitosa

**Código HTTP**

```http
200 OK
```

**Ejemplo**

```json
[
  {
    "id": 1,
    "cliente": "Carlos",
    "plato": "Pizza Pepperoni",
    "precio": 12.5,
    "entregado": false
  },
  {
    "id": 2,
    "cliente": "Ana",
    "plato": "Hamburguesa Doble",
    "precio": 9.8,
    "entregado": true
  }
]
```

---

# 2. Consultar pedido por ID

Permite obtener la información de un pedido específico.

### Endpoint

```http
GET /api/pedidos/{id}
```

### Ejemplo

```http
GET /api/pedidos/1
```

### Respuesta Exitosa

**Código HTTP**

```http
200 OK
```

**Ejemplo**

```json
{
  "id": 1,
  "cliente": "Carlos",
  "plato": "Pizza Pepperoni",
  "precio": 12.5,
  "entregado": false
}
```

### Pedido no encontrado

**Código HTTP**

```http
404 Not Found
```

**Ejemplo**

```json
{
  "error": "No existe el pedido con ID: 99"
}
```

---

# 3. Crear pedido

Registra un nuevo pedido en el sistema.

### Endpoint

```http
POST /api/pedidos
```

### Body

```json
{
  "cliente": "Luis",
  "plato": "Tacos al Pastor",
  "precio": 8.5
}
```

### Respuesta Exitosa

**Código HTTP**

```http
201 Created
```

**Ejemplo**

```json
{
  "id": 3,
  "cliente": "Luis",
  "plato": "Tacos al Pastor",
  "precio": 8.5,
  "entregado": false
}
```

### Error de validación

**Código HTTP**

```http
400 Bad Request
```

**Ejemplo**

```json
{
  "error": "El precio debe ser mayor a 0"
}
```

---

# 4. Marcar pedido como entregado

Actualiza el estado del pedido a entregado.

### Endpoint

```http
PUT /api/pedidos/{id}/entregar
```

### Ejemplo

```http
PUT /api/pedidos/1/entregar
```

### Respuesta Exitosa

**Código HTTP**

```http
204 No Content
```

---

# 5. Eliminar pedido

Elimina un pedido existente.

### Endpoint

```http
DELETE /api/pedidos/{id}
```

### Ejemplo

```http
DELETE /api/pedidos/1
```

### Respuesta Exitosa

**Código HTTP**

```http
204 No Content
```

### Pedido no encontrado

**Código HTTP**

```http
404 Not Found
```

**Ejemplo**

```json
{
  "error": "No existe el pedido con ID: 99"
}
```

---

# Códigos HTTP Utilizados

| Código          | Significado                                    |
| --------------- | ---------------------------------------------- |
| 200 OK          | Consulta realizada correctamente               |
| 201 Created     | Recurso creado exitosamente                    |
| 204 No Content  | Operación realizada sin contenido de respuesta |
| 400 Bad Request | Datos enviados inválidos                       |
| 404 Not Found   | Recurso solicitado no existe                   |

---

# Arquitectura Aplicada

La aplicación sigue una arquitectura por capas:

* **Modelo (Pedido):** representa la información de cada pedido.
* **Controlador (PedidoController):** expone los endpoints REST.
* **Servicio (PedidoService):** contiene la lógica de negocio.
* **Repositorio (PedidoRepository):** administra el almacenamiento de datos en memoria.

Además, se utiliza inyección de dependencias mediante constructor para desacoplar los componentes y facilitar el mantenimiento de la aplicación.
