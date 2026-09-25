# FoodExpress

## Descripción

FoodExpress es una API REST desarrollada con **Java y Spring Boot** que permite gestionar los pedidos de una tienda de domicilios.

El sistema permite:

* Registrar pedidos.
* Consultar todos los pedidos.
* Buscar pedidos por identificador.
* Marcar pedidos como entregados.
* Eliminar pedidos.

El proyecto fue desarrollado aplicando una arquitectura por capas y conceptos fundamentales de Spring Boot como la inyección de dependencias, separación de responsabilidades y creación de APIs RESTful.

---

## Objetivo

Desarrollar una API REST sencilla que permita administrar el ciclo básico de los pedidos de una tienda de domicilios, aplicando los conceptos de Spring Boot vistos durante el desarrollo de la asignatura.

---

## Tecnologías utilizadas

* **Java**
* **Spring Boot**
* **Spring Web**
* **Maven**
* **JSON**
* **Git / GitHub**

---

## Arquitectura del proyecto

El proyecto utiliza una arquitectura por capas:

```text
FoodExpress
│
├── controlador
│   └── PedidoController
│
├── servicio
│   ├── PedidoService
│   └── PedidoServiceImpl
│
├── repositorio
│   ├── PedidoRepository
│   └── PedidoRepositoryMemoria
│
├── modelo
│   └── Pedido
│
└── FoodExpressApplication
```

### Modelo

La clase `Pedido` representa la información de cada pedido.

### Controlador

`PedidoController` recibe las peticiones HTTP y expone los endpoints de la API.

### Servicio

`PedidoService` y `PedidoServiceImpl` contienen las operaciones y reglas de negocio relacionadas con los pedidos.

### Repositorio

`PedidoRepository` y `PedidoRepositoryMemoria` se encargan de almacenar y consultar los pedidos en memoria.

---

## Documentación

Para consultar información más detallada sobre cada parte del proyecto:

* 📌 **[`ENDPOINTS.md`](ENDPOINTS.md)**
  Explica las rutas disponibles, métodos HTTP, parámetros, cuerpos JSON y códigos de respuesta.

* ⚙️ **[`SERVICE.md`](SERVICE.md)**
  Explica la capa de servicio, sus métodos, validaciones y comunicación con el repositorio.

---

## Modelo de datos

Cada pedido contiene:

| Campo       | Tipo      | Descripción                       |
| ----------- | --------- | --------------------------------- |
| `id`        | `Long`    | Identificador del pedido          |
| `cliente`   | `String`  | Nombre del cliente                |
| `plato`     | `String`  | Plato solicitado                  |
| `precio`    | `double`  | Precio del pedido                 |
| `entregado` | `boolean` | Indica si el pedido fue entregado |

Ejemplo:

```json
{
  "id": 1,
  "cliente": "Carlos",
  "plato": "Pizza Pepperoni",
  "precio": 12.50,
  "entregado": false
}
```

---

## Endpoints principales

La API utiliza la siguiente dirección base:

```text
http://localhost:8080/api/pedidos
```

| Método   | Endpoint                     | Función               |
| -------- | ---------------------------- | --------------------- |
| `GET`    | `/api/pedidos`               | Listar pedidos        |
| `GET`    | `/api/pedidos/{id}`          | Consultar un pedido   |
| `POST`   | `/api/pedidos`               | Crear un pedido       |
| `PUT`    | `/api/pedidos/{id}/entregar` | Marcar como entregado |
| `DELETE` | `/api/pedidos/{id}`          | Eliminar un pedido    |

Para conocer el funcionamiento y ejemplos de cada endpoint, consultar **[`ENDPOINTS.md`](docs/ENDPOINTS.md)**.

---

## Códigos HTTP

La API utiliza códigos de estado HTTP para indicar el resultado de las operaciones:

| Código            | Significado                                     |
| ----------------- | ----------------------------------------------- |
| `200 OK`          | Consulta realizada correctamente                |
| `201 Created`     | Pedido creado correctamente                     |
| `204 No Content`  | Operación realizada correctamente sin contenido |
| `400 Bad Request` | Datos enviados no válidos                       |
| `404 Not Found`   | Pedido no encontrado                            |

---

## Almacenamiento

Actualmente los pedidos se almacenan **en memoria**, por lo que los datos se mantienen mientras la aplicación está ejecutándose.

Para realizar el almacenamiento se utiliza:

* `ConcurrentHashMap`
* `AtomicLong`

No se requiere una base de datos externa para ejecutar el proyecto.

---

## Configuración

La aplicación utiliza el puerto `8080`.

Archivo:

```text
src/main/resources/application.properties
```

Configuración:

```properties
spring.application.name=foodexpress
server.port=8080
```

Por lo tanto, la aplicación estará disponible en:

```text
http://localhost:8080
```

---

## Ejecución del proyecto

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

### 2. Entrar al proyecto

```bash
cd foodexpress
```

### 3. Ejecutar la aplicación

Con Maven:

```bash
mvn spring-boot:run
```

También puede ejecutarse desde el IDE utilizando la clase principal de Spring Boot.

Una vez iniciada la aplicación, la API estará disponible en:

```text
http://localhost:8080/api/pedidos
```

---

## Ejemplos de prueba

### Crear un pedido

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/pedidos" `
-Method Post `
-ContentType "application/json" `
-Body '{"cliente":"Carlos","plato":"Pizza Pepperoni","precio":12.50}'
```

### Consultar pedidos

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/pedidos"
```

### Consultar un pedido

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/pedidos/1"
```

### Marcar como entregado

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/pedidos/1/entregar" `
-Method Put
```

### Eliminar un pedido

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/pedidos/1" `
-Method Delete
```

---

## Conceptos de Spring Boot aplicados

Durante el desarrollo se aplicaron los siguientes conceptos:

* Spring Boot.
* Spring MVC.
* `@RestController`.
* `@RequestMapping`.
* `@GetMapping`.
* `@PostMapping`.
* `@PutMapping`.
* `@DeleteMapping`.
* `@RequestBody`.
* `@PathVariable`.
* Inyección de dependencias por constructor.
* `@Service`.
* `@Repository`.
* Interfaces e implementaciones.
* Arquitectura por capas.
* APIs RESTful.
* JSON.
* Códigos de estado HTTP.
* Manejo de excepciones.

---

## Documentación del proyecto

```text
README.md
│
├── ENDPOINTS.md
│   └── Rutas y funcionamiento de la API
│
└── SERVICE.md
    └── Lógica y funcionamiento de la capa de servicio
```

El `README.md` presenta una visión general del proyecto, mientras que los archivos de documentación contienen información específica sobre el funcionamiento interno de la API.

---

## Autor

Proyecto académico desarrollado como práctica de Spring Boot para comprender la creación de APIs REST y la aplicación de una arquitectura por capas.
