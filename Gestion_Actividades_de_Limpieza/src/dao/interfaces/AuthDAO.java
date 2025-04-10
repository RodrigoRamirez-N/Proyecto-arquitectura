package dao.interfaces;

import java.sql.SQLException;

public interface AuthDAO {
    
    boolean autenticar(String nombre, String contrasena) throws SQLException; // Autenticar al usuario - verificar si existe en la base de datos y si la contraseña es correcta
    void cerrarSesion(int idUsuario) throws SQLException; // Cerrar sesión del usuario - volver a la pantalla de inicio de sesión
    void crearAdministrador(String nombre, String contrasena) throws SQLException; // Crear un nuevo administrador - agregar un nuevo usuario a la base de datos
    void eliminarAdministrador(int idUsuario) throws SQLException; // Eliminar un administrador - eliminar un usuario de la base de datos
    void cambiarContrasena(int idUsuario, String nuevaContrasena) throws SQLException; // Cambiar la contraseña del usuario - actualizar la contraseña en la base de datos

}
