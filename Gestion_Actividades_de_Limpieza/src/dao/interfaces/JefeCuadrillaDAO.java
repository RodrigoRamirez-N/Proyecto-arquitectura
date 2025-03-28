package dao.interfaces;

import java.sql.SQLException;

import model.JefeCuadrilla;

public interface JefeCuadrillaDAO {

    // CRUD basico
    int createJefeCuadrilla(JefeCuadrilla jefeCuadrilla) throws SQLException; // Ajustar el tipo de retorno según la implementación
    JefeCuadrilla readJefeCuadrilla(int idJefeCuadrilla) throws SQLException;
    JefeCuadrilla updateJefeCuadrilla(JefeCuadrilla jefeCuadrilla) throws SQLException;
    boolean deleteJefeCuadrilla(int idJefeCuadrilla) throws SQLException;

}
