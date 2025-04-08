package util;

public class session {
    public static int idUsuario;
    public static String nombreUsuario;

    public static void cerrarSesion() {
        idUsuario = 0;
        nombreUsuario = null;
        System.out.println("Sesión cerrada.");
    }

    public static boolean sesionActiva() {
        return nombreUsuario != null;
    }
}
