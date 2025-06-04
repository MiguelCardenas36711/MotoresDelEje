# 🚗 Motores Del Eje

**Motores Del Eje** es un concesionario especializado en la compra y venta de vehículos de segunda mano. Nos enfocamos en ofrecer un inventario de calidad y en brindar una excelente experiencia al cliente.

---

## 🧩 Estructura de Clases

### 🔹 Clase Padre: `Vehiculos`

Esta clase sirve como base para todos los tipos de vehículos registrados en el sistema.

---

### 🔸 Clases Hijas

#### 🚘 `Carros`
Representa todos los automóviles en el inventario.

#### 🏍️ `Motos`
Contiene los datos de las motocicletas disponibles para la venta.

#### 👤 `Clientes`
Contiene información relevante de las personas interesadas o que han realizado una compra/venta.

---

## 🏷️ Atributos Comunes de los Vehículos

- `Placa`
- `Marca`
- `Modelo`
- `Año`
- `Color`
- `Kilometraje`
- `Fecha de Ingreso`
- `Fecha de Venta`
- `Precio`
- `Estado` (Disponible / Vendido / Reservado)
- `Tipo de Combustible`
- `Transmisión` (Manual / Automática)
- `Número de Dueños Anteriores`

---

## ⚙️ Métodos del Sistema

- `crearContrato()`  
  Crea el contrato de compraventa entre el cliente y el concesionario.

- `venta()`  
  Registra la venta de un vehículo y actualiza su estado.

- `actualizarVehiculos()`  
  Permite modificar datos de vehículos ya ingresados (como el precio, color, o kilometraje).

- `filtrarVehiculos()` 
  Para búsquedas según atributos (ej. marca, precio, año)

- `agregarCliente()`
  Para registrar nuevos clientes

- `generarReporte()`
  Para obtener reportes de ventas y stock

---

## 🗃️ Ejemplo de Registro

```json
{
  "tipo": "Carro",
  "placa": "XYZ123",
  "marca": "Toyota",
  "modelo": "Corolla",
  "año": 2018,
  "color": "Gris",
  "kilometraje": 45000,
  "fechaIngreso": "2025-03-15",
  "precio": 35000,
  "estado": "Disponible"
}
