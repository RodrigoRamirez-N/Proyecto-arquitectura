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
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_CreateActividad", 9);
            conexion.comando.setString(1, actividad.getDetalles());
            conexion.comando.setString(2, actividad.getTipoActividad());
            conexion.comando.setDate(3, new java.sql.Date(actividad.getFecha().getTime()));
            conexion.comando.setString(4, actividad.getEstado());
            conexion.comando.setString(5, actividad.getImagenEvidencia());
            conexion.comando.setInt(6, actividad.getCuadrilla_id());
            conexion.comando.setInt(7, actividad.getCve_colonia());
            conexion.comando.setInt(8, actividad.getUsuario_registro_id()); // id del user que registra la actividad
            // El último parámetro es un OUT para obtener el nuevo ID de la actividad creada
            conexion.registerOutParameter(9, Types.INTEGER);
            conexion.comando.execute();
    
            int nuevoId = conexion.comando.getInt(9);
            actividad.setActividad_id(nuevoId);
            return actividad;
        } finally {
            conexion.closeConnection();
        }
    }

    @Override
    public Actividad read(int id) throws SQLException {
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_GetActividadById", 1);
            conexion.comando.setInt(1, id);
            ResultSet rs = conexion.executeResultSet();
            if (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getInt("actividad_id"),
                    rs.getString("detalles"),
                    rs.getString("tipoActividad"),
                    rs.getDate("Fecha"),
                    rs.getString("imagenEvidencia"),
                    rs.getString("estado"),
                    rs.getInt("cuadrilla_id"),
                    rs.getInt("cve_colonia"),
                    rs.getInt("usuario_registro_id")
                );
                return actividad;
            }
            return null;
        } finally {
            conexion.closeConnection();
        }
    }

    @Override
    public Actividad update(Actividad actividad) throws SQLException {
        Conexion conexion = new Conexion();
        try {
            conexion.prepareCall("sp_UpdateActividad", 9); // 7 parámetros
            conexion.comando.setInt(1, actividad.getActividad_id());
            conexion.comando.setString(2, actividad.getDetalles());
            conexion.comando.setString(3, actividad.getTipoActividad());
            conexion.comando.setDate(4, new java.sql.Date(actividad.getFecha().getTime()));
            conexion.comando.setString(5, actividad.getImagenEvidencia());
            conexion.comando.setString(6, actividad.getEstado());
            conexion.comando.setInt(7, actividad.getCuadrilla_id());
            conexion.comando.setInt(8, actividad.getCve_colonia());
            conexion.comando.setInt(9, actividad.getUsuario_registro_id()); // id del user que actualiza la actividad
            
            conexion.comando.executeUpdate();
            return read(actividad.getActividad_id()); // Recuperar la actividad actualizada
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
                    rs.getString("detalles"),
                    rs.getString("tipoActividad"),
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
                    rs.getString("detalles"),
                    rs.getString("tipoActividad"),
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
                    rs.getString("detalles"),
                    rs.getString("tipoActividad"),
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
    public List<Actividad> getByFecha(java.sql.Date fecha) throws SQLException {
        List<Actividad> lista = new ArrayList<>();
        try {
            conexion.prepareCall("sp_GetActividadesByFecha", 1);
            conexion.comando.setDate(1, fecha);
            ResultSet rs = conexion.executeResultSet();

            while (rs.next()) {
                Actividad actividad = new Actividad(
                    rs.getInt("actividad_id"),
                    rs.getString("detalles"),
                    rs.getString("tipoActividad"),
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
