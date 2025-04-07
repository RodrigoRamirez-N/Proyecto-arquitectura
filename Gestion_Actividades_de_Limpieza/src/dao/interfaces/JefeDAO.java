package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import model.Jefe;

public interface JefeDAO {

    // CRUD basico
    int createJefeCuadrilla(Jefe jefeCuadrilla) throws SQLException; // Ajustar el tipo de retorno según la implementación
    Jefe readJefeCuadrilla(int idJefeCuadrilla) throws SQLException;
    Jefe updateJefeCuadrilla(Jefe jefeCuadrilla) throws SQLException;
    boolean deleteJefeCuadrilla(int idJefeCuadrilla) throws SQLException;

    // Consultas concretas
    List<Jefe> getAllJefesCuadrilla() throws SQLException; // listar todos los jefes de cuadrilla

}
