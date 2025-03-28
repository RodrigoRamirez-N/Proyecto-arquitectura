package dao.interfaces;

import java.sql.SQLException;

import model.Usuario;

public interface AuthDAO {

    
    Usuario autenticar(String nombre, String contrasena) throws SQLException; // Autenticar al usuario - verificar si existe en la base de datos y si la contraseña es correcta
    void cerrarSesion(int idUsuario) throws SQLException; // Cerrar sesión del usuario - volver a la pantalla de inicio de sesión
    Usuario obtenerUsuarioPorId(int idUsuario) throws SQLException; // Obtener información del usuario por su ID - para mostrar en la pantalla del menú como loggeado : opcional mas bien seria en la interfaz de usuario

}
