# Taller 8: Interfaz gráfica con persistencia en MySQL

Programación 1 — Universidad Libre — Docente: Andrés Castaño

Aplicación de escritorio (Swing + JDBC + MySQL) para llevar el inventario de la tienda de Don Andres: registrar, consultar, actualizar y eliminar productos organizados por categoría.

## Requisitos

- JDK 21
- Maven (o NetBeans, que ya lo incluye)
- MySQL en `localhost:3306` (por ejemplo, con XAMPP)

## Pasos para ejecutar

1. **Encender MySQL** (en XAMPP: botón *Start* junto a MySQL).
2. **Crear la base de datos:** ejecutar el archivo `script.sql` en MySQL (phpMyAdmin → pestaña *SQL*, o MySQL Workbench). Crea la base `tienda`, las tablas `categoria` y `producto`, y datos de prueba.
3. **Revisar usuario y clave:** en `src/main/java/com/github/santiago/conexion/Conexion.java` están `USER = "root"` y `PASSWORD = ""`. Si tu MySQL usa otra clave, cámbiala ahí.
4. **Ejecutar el proyecto:**
   - Desde NetBeans: abrir el proyecto y pulsar *Run* (clase principal `com.github.santiago.App`).
   - Desde la terminal, en la carpeta del proyecto:
     ```
     mvn compile exec:java -Dexec.mainClass=com.github.santiago.App
     ```

Al iniciar, la consola muestra `Conexión exitosa` y se abre la ventana con los productos guardados.

## Uso de la ventana

- **Guardar:** crea un producto con los datos del formulario.
- **Actualizar:** hacer clic en una fila de la tabla, modificar los datos y pulsar *Actualizar*.
- **Eliminar:** seleccionar una fila y pulsar *Eliminar* (pide confirmación).
- **Limpiar:** vacía el formulario.

## Estructura del proyecto

| Paquete | Contenido |
|---|---|
| `conexion` | `Conexion`: abre la conexión a MySQL |
| `model` | `Categoria` y `Producto` (un producto tiene una categoría: composición) |
| `dao` | `CategoriaDAO` / `ProductoDAO` (interfaces) y sus implementaciones `...Impl` con todo el SQL |
| `vista` | `VentanaInventario`: la interfaz gráfica |
| (raíz) | `App`: método `main` |

Las respuestas escritas del ejercicio 6 (partes c y d) están en `respuestas.md`.
## INTEGRANTES 
Nicole Camila Abril 
Gabriel Enrique Pacheco 
Santiago Peña Pacheco 
Julieta Quevedo González 
