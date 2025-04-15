package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import model.Colonia;

public interface ColoniaDAO {

    int create(Colonia colonia) throws SQLException; // ajustar el tipo de retorno según necesites para llenar la tabla colonia de la bd utilizando el excel
    Colonia read(int id) throws SQLException; // leer una colonia por su id
    void update(Colonia colonia) throws SQLException; // actualizar una colonia por su id
    boolean delete(int id) throws SQLException; // eliminar una colonia por su id

    // Consultas concretas
    List<Colonia> getAll(); // listar todas las colonias
    List<Colonia> getColoniasPorTipoActividad(String tipoActividad); // listar colonias por tipo de actividad

}
