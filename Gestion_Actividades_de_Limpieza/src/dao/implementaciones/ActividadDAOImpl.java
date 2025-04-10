package dao.implementaciones;

import dao.interfaces.ActividadDAO;
import model.Actividad;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadDAOImpl implements ActividadDAO {

    private Conexion conexion;

    public ActividadDAOImpl() {
        conexion = new Conexion();
    }

    @Override
    public Actividad create(Actividad actividad) throws SQLException {
        try {
            conexion.prepareCall("sp_CreateActividad", 8);
            conexion.comando.setString(1, actividad.getDescripcionActivdad());
            conexion.comando.setDate(2, new java.sql.Date(actividad.getFechaActividad().getTime()));
            conexion.comando.setString(3, actividad.getEvidenciaURL());
            conexion.comando.setString(4, actividad.getEstado());
            conexion.comando.setInt(5, actividad.getIdCuadrilla());
            conexion.comando.setInt(6, actividad.getIdColonia());
            conexion.comando.setInt(7, actividad.getIdUsuario());
            conexion.registerOutParameter(8, Types.INTEGER);
            conexion.execute();

            int id = conexion.comando.getInt(8);
            actividad.setIdActividad(id);
            System.out.println("Actividad creada con ID: " + id);
            return actividad;
        } catch (SQLException e) {
            System.err.println("Error al crear la actividad: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexion.closeConnection();
        }
        return null;
    }

    @Override
    public Actividad read(int id) throws SQLException {
        try {
            conexion.prepareCall("sp_GetActividadById", 1);
            conexion.comando.setInt(1, id);
            ResultSet rs = conexion.executeResultSet();

            if (rs.next()) {
                return new Actividad(
                    rs.getInt("actividad_id"),
                    rs.getString("Descripcion"),
                    rs.getDate("Fecha"),
                    rs.getString("imagenEvidencia"),
                    rs.getString("estado"),
                    rs.getInt("cuadrilla_id"),
                    rs.getInt("cve_colonia"),
                    rs.getInt("usuario_registro_id")
                );
            }

        } finally {
            conexion.closeConnection();
        }
        return null;
    }

    @Override
    public Actividad update(Actividad actividad) throws SQLException {
        try {
            conexion.prepareCall("sp_UpdateActividad", 7);
            conexion.comando.setInt(1, actividad.getIdActividad());
            conexion.comando.setString(2, actividad.getDescripcionActivdad());
            conexion.comando.setDate(3, new java.sql.Date(actividad.getFechaActividad().getTime()));
            conexion.comando.setString(4, actividad.getEvidenciaURL());
            conexion.comando.setString(5, "En Proceso"); // puedes cambiar el estado si lo manejas desde el modelo
            conexion.comando.setInt(6, actividad.getIdCuadrilla());
            conexion.comando.setInt(7, actividad.getIdColonia());

            conexion.execute();
            return actividad;
        } finally {
            conexion.closeConnection();
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_DeleteActividad", 1);
            conexion.comando.setInt(1, id);
            conexion.execute();
            System.out.println("Actividad eliminada con ID: " + id);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la actividad: " + e.getMessage());
        } finally {
            conexion.closeConnection();
        }
        return false;
    }

    @Override
    public List<Actividad> getAll() throws SQLException {
        List<Actividad> lista = new ArrayList<>();
        try {
            conexion.prepareCall("sp_GetAllActividades", 0);
            ResultSet rs = conexion.executeResultSet();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getInt("actividad_id"),
                    rs.getString("Descripcion"),
                    rs.getDate("Fecha"),
                    rs.getString("imagenEvidencia"),
                    rs.getString("estado"),
                    rs.getInt("cuadrilla_id"),
                    rs.getInt("cve_colonia"),
                    rs.getInt("usuario_registro_id")
                );
                lista.add(actividad);
            }

        } finally {
            conexion.closeConnection();
        }

        return lista;
    }

    @Override
    public List<Actividad> getByColonia(int idColonia) throws SQLException {
        List<Actividad> lista = new ArrayList<>();
        try {
            conexion.prepareCall("sp_GetActividadesByColonia", 1);
            conexion.comando.setInt(1, idColonia);
            ResultSet rs = conexion.executeResultSet();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getInt("actividad_id"),
                    rs.getString("Descripcion"),
                    rs.getDate("Fecha"),
                    rs.getString("imagenEvidencia"),
                    rs.getString("estado"),
                    rs.getInt("cuadrilla_id"),
                    rs.getInt("cve_colonia"),
                    rs.getInt("usuario_registro_id")
                );
                lista.add(actividad);
            }

        } finally {
            conexion.closeConnection();
        }

        return lista;
    }

    @Override
    public List<Actividad> getByCuadrilla(int idCuadrilla) throws SQLException {
        List<Actividad> lista = new ArrayList<>();
        try {
            conexion.prepareCall("sp_GetActividadesByCuadrilla", 1);
            conexion.comando.setInt(1, idCuadrilla);
            ResultSet rs = conexion.executeResultSet();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getInt("actividad_id"),
                    rs.getString("Descripcion"),
                    rs.getDate("Fecha"),
                    rs.getString("imagenEvidencia"),
                    rs.getString("estado"),
                    rs.getInt("cuadrilla_id"),
                    rs.getInt("cve_colonia"),
                    rs.getInt("usuario_registro_id")
                );
                lista.add(actividad);
            }

        } finally {
            conexion.closeConnection();
        }

        return lista;
    }

    @Override
    public List<Actividad> getByFecha(String fecha) throws SQLException {
        List<Actividad> lista = new ArrayList<>();
        try {
            conexion.prepareCall("sp_GetActividadesByFecha", 1);
            conexion.comando.setString(1, fecha);
            ResultSet rs = conexion.executeResultSet();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getInt("actividad_id"),
                    rs.getString("Descripcion"),
                    rs.getDate("Fecha"),
                    rs.getString("imagenEvidencia"),
                    rs.getString("estado"),
                    rs.getInt("cuadrilla_id"),
                    rs.getInt("cve_colonia"),
                    rs.getInt("usuario_registro_id")
                );
                lista.add(actividad);
            }

        } finally {
            conexion.closeConnection();
        }

        return lista;
    }
}
