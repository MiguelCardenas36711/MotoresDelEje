# 🚗 Motores Del Eje

**Motores Del Eje** es un concesionario especializado en la compra y venta de vehículos de primera y segunda mano. Nos enfocamos en ofrecer un inventario de calidad y en brindar una excelente experiencia al cliente.

## 🏁 Objetivo del sistema

Desarrollar una aplicación de escritorio en Java utilizando Swing, orientada a la gestión de un concesionario. El sistema permite manejar el inventario de vehículos, realizar ventas, generar contratos automatizados y visualizar el historial.

---

## 🧱 Arquitectura General

- **Lenguaje:** Java
- **Paradigma:** Programación Orientada a Objetos (POO)
- **Interfaz gráfica:** Java Swing
- **Persistencia:** Archivos `.txt`
- **Patrón usado:** Singleton (`InventarioSingleton`)
- **Estructura de paquetes:**
  - `model`: entidades del sistema
  - `view`: interfaz gráfica

---

## 📦 Clases del Modelo (`model`)

## 🗂️ Estructura de Clases y Funcionalidades

### `Vehiculo` _(abstracta en `model`)_
- Atributos:
  - `placa`, `marca`, `modelo`, `anio`, `color`, `kilometraje`, `fechaIngreso`, `precio`, `estado`, `combustible`, `transmision`, `duenos`
- Métodos:
  - Getters/setters
  - Constructor común
  - `toString()`, representación para ComboBox

### `Carro extends Vehiculo`
- Atributo:
  - `numeroPuertas`

### `Moto extends Vehiculo`
- Atributo:
  - `tieneSidecar` (boolean)

### `Cliente`
- Atributos: `nombre`, `apellido`, `cedula`, `sexo`, `direccion`, `telefono`, `correo`

### `InventarioVehiculos` + `InventarioSingleton`
- Métodos:
  - `agregarVehiculo(Vehiculo)`
  - `eliminarVehiculoPorPlaca(String)`
  - `editarVehiculo(String, Vehiculo)`
  - `obtenerTodos(): List<Vehiculo>`
  - `filtrar(criterio)`
  - `buscarPorPlaca(String)`
- Se accede vía `InventarioSingleton.getInstancia()`

### `RegistroVentas`
- Método:
  - `registrarVenta(Cliente, Vehiculo, LocalDate)` → escribe a `ventas.txt`

### `PersistenciaVehiculos`
- Métodos:
  - `guardarInventario(List<Vehiculo>)`
  - `cargarInventario(): List<Vehiculo>`

---

## 🔠 Enumeraciones (`enum` en `model`)

- `Estado`: `DISPONIBLE`, `VENDIDO`, `RESERVADO`
- `Combustible`: `GASOLINA`, `DIESEL`, `ELECTRICO`
- `Transmision`: `MANUAL`, `AUTOMATICA`

---

## 💾 Archivos de Persistencia

- `vehiculos.txt`: contiene datos serializados de vehículos por línea
- `ventas.txt`: contiene datos de cada venta separados por `;`
- Ambos se sobreescriben o actualizan manualmente tras cada acción

---

## 🖥️ Interfaz Gráfica (`view`)

### 🎯 `LoginFrame`
- Estilo oscuro
- Campos centrados: usuario/contraseña
- Validación básica: `admin` / `1234`

---

### 🧭 `VentanaPrincipal`
- Distribución:
  - `BorderLayout`: panel lateral (`WEST`), panel dinámico central (`CENTER`)
  - `panelLateral`: botones de navegación
  - `panelCentral`: `CardLayout` con vistas:
    - `inicio` → mensaje y resumen
    - `inventario` → `PanelInventario`
    - `formulario` → `FormularioVehiculoPanel`
    - `venta` → `PanelVenta`
    - `historial` → `PanelHistorialVentas`
- Estilo:
  - Tonos grises oscuros (`#2A2A2A`, `#3C3C3C`, etc.)
  - Tipografía: `Segoe UI`, clara

---

## 🧩 Paneles Principales

### 🏁 Panel de Inicio
- Vista inicial con mensaje:
  > "Bienvenido a Motores del Eje"

---

### 📦 `PanelInventario`
- Tabla de vehículos
- **Funciones:**
  - Buscar/filtro dinámico por placa o marca
  - Refrescar automáticamente al volver
  - Botón “Editar”:
    - Abre diálogo de edición
    - Actualiza datos en `vehiculos.txt`
  - Botón “Eliminar”:
    - Elimina registro tras confirmar
    - Refresca tabla
- Estética: color de fondo en base a su estado verde(Disponible), amarillo(Reservado) o rojo(Vendido)

---

### ➕ `FormularioVehiculoPanel`
- Formulario con:
  - Campos adaptables (tipo `Carro` o `Moto`)
  - Número de puertas vs. tiene sidecar
  - Validación básica
- Botón "Guardar":
  - Guarda en `vehiculos.txt`
  - Vacía el formulario

---

### 📝 `PanelVenta`
- Formulario del cliente con:
  - Nombre, apellido, cédula, sexo, etc.
- **Combo dinámico de vehículos disponibles**
  - Se actualiza automáticamente al abrir el panel
- Botón “Continuar”:
  - Abre `VentanaContrato`

---

### 🧾 `VentanaContrato` (`JDialog`)
- Muestra contrato con datos cliente + vehículo
- Verificación de firma (nombre del comprador)
- Al confirmar:
  - Cambia estado vehículo → `VENDIDO`
  - Llama a `RegistroVentas.registrarVenta(...)`
  - Guarda en `ventas.txt`
  - Lleva automáticamente al historial

---

### 📄 `PanelHistorialVentas`
- Tabla con datos leídos desde `ventas.txt`
- Filtro dinámico por cédula:
  - Campo de texto + `DocumentListener`
- Recarga cada vez que se accede
- Estilo oscuro con posible color por tipo de combustible (opcional)

---

## 🔁 Flujo dinámico entre paneles

- Todos los paneles se comunican mediante instancia compartida
- Al agregar, vender o eliminar datos:
  - `PanelInventario`, `PanelVenta`, `PanelHistorialVentas` se actualizan automáticamente
- `CardLayout` permite transiciones limpias

---

## 📌 Funcionalidades implementadas clave

- Gestión CRUD de vehículos (agregar, editar, eliminar)
- Visualización dinámica y persistente
- Registro de ventas con contrato y validación de firma
- Filtros en tiempo real
- Interfaz con navegación clara
- Separación limpia entre lógica (`model`) e interfaz (`view`)

---

## Diagrama de Clases

![diagramaClases](https://github.com/user-attachments/assets/36b4f051-be5a-45bb-96e2-3cd53126d016)
