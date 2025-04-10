package dao.implementaciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.interfaces.CuadrillaDAO;
import model.Cuadrilla;
import util.Conexion;

public class CuadrillaDAOImpl implements CuadrillaDAO {

    @Override
    public Cuadrilla createCuadrilla(Cuadrilla cuadrilla) {
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_CreateCuadrilla", 3);
            conexion.comando.setString(1, cuadrilla.getNombreCuadrilla());
            conexion.comando.setInt(2, cuadrilla.getIdJefeCuadrilla());
            conexion.registerOutParameter(3, Types.INTEGER);
            conexion.comando.execute();

            int nuevoId = conexion.comando.getInt(3);
            cuadrilla.setIdCuadrilla(nuevoId);
            return cuadrilla;
        } catch (SQLException e) {
            System.err.println("Error al crear cuadrilla: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return null;
    }

    @Override
    public Cuadrilla readCuadrilla(int idCuadrilla) {
        Conexion conexion = new Conexion();
        Cuadrilla cuadrilla = null;
        try {
            conexion.prepareCall("sp_GetCuadrillaById", 1);
            conexion.comando.setInt(1, idCuadrilla);
            ResultSet rs = conexion.executeResultSet();
            if (rs.next()) {
                cuadrilla = new Cuadrilla(
                    rs.getInt("jefe_id"),
                    rs.getInt("cuadrilla_id"),
                    rs.getString("NombreCuadrilla"),
                    rs.getDate("Fecha")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer cuadrilla: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return cuadrilla;
    }

    @Override
    public Cuadrilla updateCuadrilla(Cuadrilla cuadrilla) {
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_UpdateCuadrilla", 3);
            conexion.comando.setInt(1, cuadrilla.getIdCuadrilla());
            conexion.comando.setString(2, cuadrilla.getNombreCuadrilla());
            conexion.comando.setInt(3, cuadrilla.getIdJefeCuadrilla());
            conexion.comando.execute();
            return cuadrilla;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cuadrilla: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return null;
    }

    @Override
    public boolean deleteCuadrilla(int idCuadrilla) {
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_DeleteCuadrilla", 1);
            conexion.comando.setInt(1, idCuadrilla);
            conexion.comando.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cuadrilla: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return false;
    }

    @Override
    public List<Cuadrilla> getAllCuadrillas() {
        List<Cuadrilla> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_GetAllCuadrillas", 0);
            ResultSet rs = conexion.executeResultSet();
            while (rs.next()) {
                Cuadrilla c = new Cuadrilla(
                    rs.getInt("jefe_id"),
                    rs.getInt("cuadrilla_id"),
                    rs.getString("NombreCuadrilla"),
                    rs.getDate("Fecha")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todas las cuadrillas: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }

    @Override
    public List<Cuadrilla> getCuadrillasByJefe(int idJefeCuadrilla) {
        List<Cuadrilla> lista = new ArrayList<>();
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_GetCuadrillasByJefe", 1);
            conexion.comando.setInt(1, idJefeCuadrilla);
            ResultSet rs = conexion.executeResultSet();
            while (rs.next()) {
                Cuadrilla c = new Cuadrilla(
                    rs.getInt("jefe_id"),
                    rs.getInt("cuadrilla_id"),
                    rs.getString("NombreCuadrilla"),
                    rs.getDate("Fecha")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cuadrillas por jefe: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return lista;
    }
}
