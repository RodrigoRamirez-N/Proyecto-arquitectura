package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import model.Jefe;

public interface JefeDAO {

    // CRUD basico
    int create(Jefe jefeCuadrilla) throws SQLException; // Ajustar el tipo de retorno según la implementación
    Jefe read(int idJefeCuadrilla) throws SQLException;
    void update(Jefe jefeCuadrilla) throws SQLException;
    boolean delete(int idJefeCuadrilla) throws SQLException;

    // Consultas concretas
    List<Jefe> getAllJefesCuadrilla() throws SQLException; // listar todos los jefes de cuadrilla
    void asignarJefeACuadrilla(int idJefeCuadrilla, int idCuadrilla) throws SQLException; // asignar jefe a cuadrilla
    void removerJefeDeCuadrilla(int idCuadrilla) throws SQLException; // remover jefe de cuadrilla
}
