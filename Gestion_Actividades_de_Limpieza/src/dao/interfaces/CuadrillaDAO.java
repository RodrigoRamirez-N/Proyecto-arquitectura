package dao.interfaces;

import java.util.List;

import model.Cuadrilla;

public interface CuadrillaDAO {

    // CRUD basico

    Cuadrilla createCuadrilla(Cuadrilla cuadrilla);
    Cuadrilla readCuadrilla(int idCuadrilla);
    Cuadrilla updateCuadrilla(Cuadrilla cuadrilla);
    boolean deleteCuadrilla(int idCuadrilla);

    // Consultas concretas

    List<Cuadrilla> getAllCuadrillas(); // listar todas las cuadrillas
    List<Cuadrilla> getCuadrillasByJefe(int idJefeCuadrilla); // listar cuadrillas por su jefe de cuadrilla

}
