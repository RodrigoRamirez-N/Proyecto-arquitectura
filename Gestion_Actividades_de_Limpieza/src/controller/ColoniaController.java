package controller;

import dao.implementaciones.ColoniaDAOImpl;
import dao.interfaces.ColoniaDAO;
import model.Colonia;

import java.sql.SQLException;
import java.util.List;

public class ColoniaController {

    private ColoniaDAO coloniaDAO;

    public ColoniaController() {
        this.coloniaDAO = new ColoniaDAOImpl(); // Usamos la implementación DAO
    }

    // Método para crear una colonia
    public int crearColonia(String nombreColonia) {
        Colonia nuevaColonia = new Colonia(0, nombreColonia);
        try {
            return coloniaDAO.create(nuevaColonia); // Retorna el ID
        } catch (SQLException e) {
            System.out.println("Error al crear colonia: " + e.getMessage());
            return -1;
        }
    }

    // Método para leer una colonia por su ID
    public Colonia obtenerColoniaPorId(int idColonia) {
        try {
            return coloniaDAO.read(idColonia);
        } catch (SQLException e) {
            System.out.println("Error al obtener colonia: " + e.getMessage());
            return null;
        }
    }

    // Método para actualizar una colonia
    public void actualizarColonia(int idColonia, String nombreColonia) {
        Colonia colonia = new Colonia(idColonia, nombreColonia);
        try {
            coloniaDAO.update(colonia);
        } catch (SQLException e) {
            System.out.println("Error al actualizar colonia: " + e.getMessage());
        }
    }

    // Método para eliminar una colonia
    public boolean eliminarColonia(int idColonia) {
        try {
            return coloniaDAO.delete(idColonia);
        } catch (SQLException e) {
            System.out.println("Error al eliminar colonia: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener todas las colonias
    public List<Colonia> obtenerTodasLasColonias() {
        try {
            return coloniaDAO.getAll();
        } catch (Exception e) {
            System.out.println("Error al obtener todas las colonias: " + e.getMessage());
            return null;
        }
    }
}
