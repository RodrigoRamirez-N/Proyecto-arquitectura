package controller;

import dao.interfaces.JefeDAO;
import dao.implementaciones.JefeDAOImpl;
import model.Jefe;

import java.sql.SQLException;
import java.util.List;

public class JefeController {

    // El controlador se encarga de la logica de negocio y de las validaciones
    // OJO que el controlador puede volverse más simple si incluímos microservicios para manejar la lógica de negocio
    // asi el controlador solo se encarga de manejar las peticiones y respuestas entre la vista y el servicio

    private JefeDAO jefeDAO;

    public JefeController() {
        this.jefeDAO = new JefeDAOImpl();
    }

    // Crear un nuevo jefe
    public int crearJefe(String nombre, String contrasena, String telefono) {
        try {
            Jefe nuevoJefe = new Jefe(0, nombre, contrasena, "jefe", telefono);
            return jefeDAO.create(nuevoJefe);
        } catch (SQLException e) {
            System.err.println("Error al crear jefe: " + e.getMessage());
            return -1;
        }
    }

    // Leer un jefe por ID
    public Jefe obtenerJefe(int idJefe) {
        try {
            return jefeDAO.read(idJefe);
        } catch (SQLException e) {
            System.err.println("Error al obtener jefe: " + e.getMessage());
            return null;
        }
    }

    // Actualizar un jefe existente
    public void actualizarJefe(int idJefe, String nombre, String contrasena, String telefono) {
        try {
            Jefe jefeExistente = new Jefe(idJefe, nombre, contrasena, "jefe", telefono);
            jefeDAO.update(jefeExistente);
        } catch (SQLException e) {
            System.err.println("Error al actualizar jefe: " + e.getMessage());
        }
    }

    // Eliminar un jefe por ID
    public boolean eliminarJefe(int idJefe) {
        try {
            return jefeDAO.delete(idJefe);
        } catch (SQLException e) {
            System.err.println("Error al eliminar jefe: " + e.getMessage());
            return false;
        }
    }

    // Obtener todos los jefes
    public List<Jefe> obtenerTodosLosJefes() {
        try {
            return jefeDAO.getAllJefesCuadrilla();
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los jefes: " + e.getMessage());
            return null;
        }
    }

    // Asignar un jefe a una cuadrilla
    public void asignarJefeACuadrilla(int idJefe, int idCuadrilla) {
        try {
            jefeDAO.asignarJefeACuadrilla(idJefe, idCuadrilla);
        } catch (SQLException e) {
            System.err.println("Error al asignar jefe a cuadrilla: " + e.getMessage());
        }
    }

    // Remover un jefe de una cuadrilla
    public void removerJefeDeCuadrilla(int idCuadrilla) {
        try {
            jefeDAO.removerJefeDeCuadrilla(idCuadrilla);
        } catch (SQLException e) {
            System.err.println("Error al remover jefe de cuadrilla: " + e.getMessage());
        }
    }
}
