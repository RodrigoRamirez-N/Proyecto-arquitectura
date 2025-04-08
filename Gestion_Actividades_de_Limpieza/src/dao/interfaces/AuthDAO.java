package dao.interfaces;

import java.sql.SQLException;

public interface AuthDAO {
    
    boolean autenticar(String nombre, String contrasena) throws SQLException; // Autenticar al usuario - verificar si existe en la base de datos y si la contraseña es correcta
    void cerrarSesion(int idUsuario) throws SQLException; // Cerrar sesión del usuario - volver a la pantalla de inicio de sesión

}
