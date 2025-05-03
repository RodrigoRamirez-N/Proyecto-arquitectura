package controller;

import dao.implementaciones.ActividadDAOImpl;
import dao.interfaces.ActividadDAO;
import model.Actividad;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

public class ActividadController {

    private ActividadDAO actividadDAO;

    public ActividadController() {
        this.actividadDAO = new ActividadDAOImpl();
    }

    // Crear una nueva actividad
    public Actividad createActividad(String detalles, String tipoActividad, Date fecha, String evidenciaURL, 
            String estado, int idCuadrilla, int idColonia, int idUsuario) {
        try {
            Actividad actividad = new Actividad(0, detalles, tipoActividad, fecha, evidenciaURL, estado, idCuadrilla, idColonia, idUsuario);
            return actividadDAO.create(actividad);
        } catch (SQLException e) {
            System.err.println("Error al crear la actividad: " + e.getMessage());
        }
        return null;
    }

    // Obtener una actividad por ID
    public Actividad getActividadById(int id) {
        try {
            return actividadDAO.read(id);
        } catch (SQLException e) {
            System.err.println("Error al obtener la actividad: " + e.getMessage());
        }
        return null;
    }

    // Actualizar una actividad
    public Actividad updateActividad(int id, String detalles, String tipoActividad, Date fecha, 
            String evidenciaURL, String estado, int idCuadrilla, int idColonia, int idUsuario) {
        try {
            Actividad actividad = new Actividad(id, detalles, tipoActividad, fecha, evidenciaURL, 
                estado, idCuadrilla, idColonia, idUsuario);
            return actividadDAO.update(actividad); // Retorna la actividad actualizada desde BD
        } catch (SQLException e) {
            System.err.println("Error al actualizar la actividad: " + e.getMessage());
        }
        return null;
    }

    // Eliminar una actividad
    public boolean deleteActividad(int id) {
        try {
            return actividadDAO.delete(id);
        } catch (SQLException e) {
            System.err.println("Error al eliminar la actividad: " + e.getMessage());
        }
        return false;
    }

    // Obtener todas las actividades
    public List<Actividad> getAllActividades() {
        try {
            return actividadDAO.getAll();
        } catch (SQLException e) {
            System.err.println("Error al obtener las actividades: " + e.getMessage());
        }
        return null;
    }

    // Obtener actividades por colonia
    public List<Actividad> getActividadesByColonia(int idColonia) {
        try {
            return actividadDAO.getByColonia(idColonia);
        } catch (SQLException e) {
            System.err.println("Error al obtener las actividades por colonia: " + e.getMessage());
        }
        return null;
    }

    // Obtener actividades por cuadrilla
    public List<Actividad> getActividadesByCuadrilla(int idCuadrilla) {
        try {
            return actividadDAO.getByCuadrilla(idCuadrilla);
        } catch (SQLException e) {
            System.err.println("Error al obtener las actividades por cuadrilla: " + e.getMessage());
        }
        return null;
    }

    // Obtener actividades por fecha
    public List<Actividad> getActividadesByFecha(String fecha) {
        try {
            return actividadDAO.getByFecha(fecha);
        } catch (SQLException e) {
            System.err.println("Error al obtener las actividades por fecha: " + e.getMessage());
        }
        return null;
    }
}
