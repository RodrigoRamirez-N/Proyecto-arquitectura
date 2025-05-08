package util;

import model.Usuario;

public class Session {

    // Singleton Pattern: Clase para manejar la sesión de usuario
    // Esta clase asegura que solo haya una instancia de la sesión en toda la aplicación.

    private static Session instance; // Instancia única de la clase

    private Usuario usuario; // Objeto Usuario que representa al usuario autenticado

    // Constructor privado para evitar instanciación externa
    private Session() {}

    // Método público para obtener la instancia única
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void iniciarSesion(Usuario user) {
        this.usuario = user; // Asignar el objeto Usuario a la sesión
        //load to the home menu title the name of the user and behind it the id of the user
        System.out.println("Sesión iniciada como: " + user.getNombre());
        // send to home menu
    }

    public void cerrarSesion() {
        if (usuario != null) {
            System.out.println("Sesión cerrada para el usuario con ID: " + usuario.getId());
            usuario = null;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isSesionActiva() {
        return usuario != null;
    }
}