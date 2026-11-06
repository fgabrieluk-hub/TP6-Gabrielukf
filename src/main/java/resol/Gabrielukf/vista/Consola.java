package resol.Gabrielukf.vista;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/** Utilidad de entrada por consola (solo lectura de datos, sin lógica de negocio). */
public final class Consola {
    private static final Scanner SC = new Scanner(System.in);

    private Consola() { }

    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return SC.nextLine().trim();
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    public static double leerDecimal(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    /** Formato AAAA-MM-DD. Si se deja vacío, devuelve la fecha de hoy. */
    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            String t = leerTexto(mensaje);
            if (t.isEmpty()) {
                return LocalDate.now();
            }
            try {
                return LocalDate.parse(t);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use AAAA-MM-DD.");
            }
        }
    }
}
