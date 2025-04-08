package dao.implementaciones;

import java.sql.SQLException;

import dao.interfaces.AuthDAO;
import util.Conexion;
import util.session;

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
                session.idUsuario = idUsuario;
                session.nombreUsuario = nombre;
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
    public void cerrarSesion(int idUsuario) throws SQLException {
        session.cerrarSesion(); // Call the session method to close the session
        System.out.println("Sesión cerrada para el usuario con ID: " + idUsuario);
        // Dispose of the session resources if needed
        // dispose(); // Removed as the method is undefined
        // show the login screen again
    }

}
