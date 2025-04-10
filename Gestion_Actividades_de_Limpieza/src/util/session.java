package util;

public class Session {

    // Singleton Pattern: Clase para manejar la sesión de usuario
    // Esta clase asegura que solo haya una instancia de la sesión en toda la aplicación.

    private static Session instance; // Instancia única de la clase

    private int idUsuario; // Variables de sesión
    private String nombreUsuario;

    // Constructor privado para evitar instanciación externa
    private Session() {}

    // Método público para obtener la instancia única
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void iniciarSesion(int id, String nombre) {
        this.idUsuario = id;
        this.nombreUsuario = nombre;
        System.out.println("Sesión iniciada como: " + nombreUsuario);
        // send to home menu
    }

    public void cerrarSesion(int id) {
        if (id == idUsuario) {
            idUsuario = 0;
            nombreUsuario = null;
            System.out.println("Sesión cerrada para el usuario con ID: " + id);
            // dispose home menu, return to login menu
        } else {
            System.out.println("No se puede cerrar la sesión. ID de usuario no coincide.");
        }
    }
}
