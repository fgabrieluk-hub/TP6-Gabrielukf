package resol.Gabrielukf.vista;

/** Vista general: solo muestra menús y devuelve la opción elegida. */
public class MenuVista {

    public int mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Gestión de Videojuegos");
        System.out.println("2. Gestión de Ventas");
        System.out.println("0. Salir");
        return Consola.leerEntero("Seleccione una opción: ");
    }

    public int mostrarMenuVideojuegos() {
        System.out.println("\n=== GESTIÓN DE VIDEOJUEGOS ===");
        System.out.println("1. Listar videojuegos");
        System.out.println("2. Buscar videojuego por ID");
        System.out.println("3. Agregar videojuego");
        System.out.println("4. Actualizar videojuego");
        System.out.println("5. Eliminar videojuego");
        System.out.println("6. Videojuegos que necesitan reposición");
        System.out.println("7. Videojuegos disponibles para la venta");
        System.out.println("0. Volver al menú principal");
        return Consola.leerEntero("Seleccione una opción: ");
    }

    public int mostrarMenuVentas() {
        System.out.println("\n=== GESTIÓN DE VENTAS ===");
        System.out.println("1. Listar ventas");
        System.out.println("2. Buscar venta por ID");
        System.out.println("3. Registrar venta");
        System.out.println("4. Buscar ventas de un videojuego");
        System.out.println("5. Reporte de ventas del mes actual");
        System.out.println("0. Volver al menú principal");
        return Consola.leerEntero("Seleccione una opción: ");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
