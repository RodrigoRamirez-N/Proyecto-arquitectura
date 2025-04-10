package dao.implementaciones;

import java.sql.SQLException;

import dao.interfaces.AuthDAO;
import util.Conexion;
import util.Session;

public class AuthDAOImpl implements AuthDAO{

    @Override
    public boolean autenticar(String nombre, String contrasena) throws SQLException {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_AutenticarUsuario(?,?,?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setString(1, nombre);
            conn.comando.setString(2, contrasena);
            conn.comando.registerOutParameter(3, java.sql.Types.BOOLEAN); // is_authenticated
            conn.comando.registerOutParameter(4, java.sql.Types.INTEGER); // idUsuario

            conn.comando.execute();

            boolean result = conn.comando.getBoolean(3);
            int idUsuario = conn.comando.getInt(4);

            if (result) {
                System.out.println("Usuario autenticado correctamente. ID: " + idUsuario);
                Session session = Session.getInstance(); // Get the singleton instance of Session
                session.iniciarSesion(idUsuario, nombre); // Start the session with the user ID and name
                return true;
            } else {
                System.out.println("Error al autenticar usuario.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("(catch)Error al autenticar usuario: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return false;
    }

    @Override
    public void crearAdministrador(String nombre, String contrasena) throws SQLException {
        Conexion conn = new Conexion();
        try {
            String call = "{call sp_CrearAdministrador(?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setString(1, nombre);
            conn.comando.setString(2, contrasena);

            conn.comando.execute();

        } catch (SQLException e) {
            System.out.println("(catch)Error al crear administrador: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
    }

    @Override
    public void eliminarAdministrador(int idUsuario) throws SQLException {
        Conexion conn = new Conexion();
        try {
            String call = "{call sp_EliminarAdministrador(?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, idUsuario);

            conn.comando.execute();

        } catch (SQLException e) {
            System.out.println("(catch)Error al eliminar administrador: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
    }

    @Override
    public void cambiarContrasena(int idUsuario, String nuevaContrasena) throws SQLException {
        Conexion conn = new Conexion();
        try {
            String call = "{call sp_CambiarContrasena(?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, idUsuario);
            conn.comando.setString(2, nuevaContrasena);

            conn.comando.execute();

        } catch (SQLException e) {
            System.out.println("(catch)Error al cambiar contraseña: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }

    }

    @Override
    public void cerrarSesion(int idUsuario) throws SQLException {
        Session session = Session.getInstance(); // Get the singleton instance of Session
        session.cerrarSesion(idUsuario); // Close the session for the user ID
        System.out.println("Sesión cerrada para el usuario con ID: " + idUsuario);
        // Dispose of the Session resources if needed
        // dispose(); // Removed as the method is undefined
        // show the login screen again
    }

}
