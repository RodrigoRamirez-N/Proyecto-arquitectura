package controller;

import dao.implementaciones.AuthDAOImpl;
import dao.interfaces.AuthDAO;
import model.Usuario;

import java.sql.SQLException;

public class AuthController {

    private AuthDAO authDAO;

    public AuthController() {
        this.authDAO = new AuthDAOImpl(); // Usamos la implementación DAO
    }

    // Método para autenticar a un usuario
    public Usuario autenticarUsuario(String nombre, String contrasena) {
        try {
            return authDAO.autenticar(nombre, contrasena);
        } catch (SQLException e) {
            System.out.println("Error durante la autenticación: " + e.getMessage());
            return null;
        }
    }

    // Método para crear un nuevo administrador
    public void crearAdministrador(String nombre, String contrasena) {
        try {
            authDAO.crearAdministrador(nombre, contrasena);
        } catch (SQLException e) {
            System.out.println("Error al crear el administrador: " + e.getMessage());
        }
    }

    // Método para eliminar un administrador
    public void eliminarAdministrador(int idUsuario) {
        try {
            authDAO.eliminarAdministrador(idUsuario);
        } catch (SQLException e) {
            System.out.println("Error al eliminar el administrador: " + e.getMessage());
        }
    }

    // Método para cambiar la contraseña
    public void cambiarContrasena(int idUsuario, String nuevaContrasena) {
        try {
            authDAO.cambiarContrasena(idUsuario, nuevaContrasena);
        } catch (SQLException e) {
            System.out.println("Error al cambiar la contraseña: " + e.getMessage());
        }
    }

}
