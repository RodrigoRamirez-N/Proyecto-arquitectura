package dao.implementaciones;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import dao.interfaces.AuthDAO;
import model.Usuario;
import util.Conexion;
import util.Session;

public class AuthDAOImpl implements AuthDAO{

    @Override
    public Usuario autenticar(String nombre, String contrasena) throws SQLException {
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
                //System.out.println("Usuario autenticado correctamente. ID: " + idUsuario);
                Usuario usuario = new Usuario(idUsuario, nombre, contrasena, "admin");
                Session.getInstance().iniciarSesion(usuario); // Start the session with the user ID and name
                return usuario; // Return the authenticated user object
            } else {
                System.out.println("Error al autenticar usuario.");
                JOptionPane.showMessageDialog(null, "Credenciales inválidas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return null; // Return null if authentication fails
            }

        } catch (SQLException e) {
            System.out.println("(catch)Error al autenticar usuario: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return null;
    }

    @Override
    public void crearAdministrador(String nombre, String contrasena) throws SQLException {
        Conexion conn = new Conexion();
        try {
            String call = "{call sp_CrearAdmin(?,?)}";
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

}
