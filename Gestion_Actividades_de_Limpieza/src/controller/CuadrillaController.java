package controller;

import model.Cuadrilla;
import dao.interfaces.CuadrillaDAO;
import dao.implementaciones.CuadrillaDAOImpl;

import java.sql.Date;
import java.util.List;

public class CuadrillaController {

    private CuadrillaDAO cuadrillaDAO;

    public CuadrillaController() {
        this.cuadrillaDAO = new CuadrillaDAOImpl(); // Inicializa el DAO de Cuadrilla
    }

    // Crear una nueva cuadrilla
    public Cuadrilla crearCuadrilla(String nombreCuadrilla, Date fechaCreacion) {
        try {
            // Crear la nueva cuadrilla con un ID inicial de 0 (será generado por la base de datos)
            // id del jefe inicializa en 0, ya que sera asignado posteriormente
            Cuadrilla nuevaCuadrilla = new Cuadrilla(0,0, nombreCuadrilla, fechaCreacion);
            return cuadrillaDAO.createCuadrilla(nuevaCuadrilla); // Llama al DAO para crear la cuadrilla
        } catch (Exception e) {
            System.err.println("Error al crear cuadrilla: " + e.getMessage());
            return null;
        }
    }

    // Obtener una cuadrilla por su ID
    public Cuadrilla obtenerCuadrillaPorId(int idCuadrilla) {
        try {
            return cuadrillaDAO.readCuadrilla(idCuadrilla); // Llama al DAO para obtener la cuadrilla por ID
        } catch (Exception e) {
            System.err.println("Error al obtener la cuadrilla: " + e.getMessage());
            return null;
        }
    }

    // Actualizar una cuadrilla existente
    public Cuadrilla actualizarCuadrilla(int idCuadrilla, String nombreCuadrilla, int idJefeCuadrilla) {
        // Verifica si la cuadrilla existe antes de intentar actualizarla
        Cuadrilla cuadrillaExistente = obtenerCuadrillaPorId(idCuadrilla);
        if (cuadrillaExistente == null) {
            System.err.println("La cuadrilla con ID " + idCuadrilla + " no existe.");
            return null;
        }

        try {
            Cuadrilla cuadrillaActualizada = new Cuadrilla(idJefeCuadrilla, idCuadrilla, nombreCuadrilla, null);
            return cuadrillaDAO.updateCuadrilla(cuadrillaActualizada); // Llama al DAO para actualizar la cuadrilla
        } catch (Exception e) {
            System.err.println("Error al actualizar la cuadrilla: " + e.getMessage());
            return null;
        }
    }

    // Eliminar una cuadrilla por su ID
    public boolean eliminarCuadrilla(int idCuadrilla) {
        try {
            return cuadrillaDAO.deleteCuadrilla(idCuadrilla); // Llama al DAO para eliminar la cuadrilla
        } catch (Exception e) {
            System.err.println("Error al eliminar la cuadrilla: " + e.getMessage());
            return false;
        }
    }

    // Obtener todas las cuadrillas
    public List<Cuadrilla> obtenerTodasLasCuadrillas() {
        try {
            return cuadrillaDAO.getAllCuadrillas(); // Llama al DAO para obtener todas las cuadrillas
        } catch (Exception e) {
            System.err.println("Error al obtener todas las cuadrillas: " + e.getMessage());
            return null;
        }
    }

    // Obtener cuadrillas por el ID del jefe
    public List<Cuadrilla> obtenerCuadrillasPorJefe(int idJefeCuadrilla) {
        try {
            return cuadrillaDAO.getCuadrillasByJefe(idJefeCuadrilla); // Llama al DAO para obtener cuadrillas por jefe
        } catch (Exception e) {
            System.err.println("Error al obtener cuadrillas por jefe: " + e.getMessage());
            return null;
        }
    }
}
