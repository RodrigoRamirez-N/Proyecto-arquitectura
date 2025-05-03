package dao.implementaciones;

import dao.interfaces.JefeDAO;
import model.Jefe;
import java.util.List;
import util.Conexion;
import util.SHA1;

import java.sql.*;
import java.util.ArrayList;

public class JefeDAOImpl implements JefeDAO {
    
    @Override
    public int create(Jefe jefe) throws SQLException {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_CreateJefeCuadrilla(?,?,?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setString(1, jefe.getNombre());
            conn.comando.setString(2, jefe.getPassword());
            conn.comando.setString(3, jefe.getTelefono());
            conn.comando.registerOutParameter(4, Types.INTEGER);

            conn.comando.execute();

            int resultId = conn.comando.getInt(4);
            if(resultId > 0) {
                System.out.println("Jefe creado con ID: " + resultId);
                return resultId;
            } else {
                System.err.println("(ifStmt)Error al crear jefe: ID no válido");
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("(catch)Error al crear jefe: ");
            e.printStackTrace();
            return -1;
        } finally {
            conn.closeConnection();
        }
    }

    @Override
    public Jefe read(int idJefe) throws SQLException {
        Conexion conn = new Conexion();
        Jefe jefe = null;

        try{
            String call = "{call sp_GetJefeCuadrillaById(?)}";
            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idJefe);

            ResultSet rs = conn.comando.executeQuery();

            if(rs.next()){
                jefe = new Jefe(
                    rs.getInt("usuario_id"),
                    rs.getString("nombre"),
                    Integer.toString(rs.getString("Contrasenia").hashCode()),
                    rs.getString("rol"),
                    rs.getString("telefono")
                ); 
            } else {
                System.out.println("No se encontró el jefe con ID: " + idJefe);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el jefe: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return jefe;
    }

    @Override
    public void update(Jefe jefe) throws SQLException {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_UpdateJefeCuadrilla(?,?,?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, jefe.getId()); // no es para cambiar el id, es para buscar el jefe
            conn.comando.setString(2, jefe.getNombre());
            conn.comando.setString(3, jefe.getPassword());
            conn.comando.setString(4, jefe.getTelefono());

            int rowsAffected = conn.comando.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Jefe actualizado con ID: " + jefe.getId());
            } else {
                System.err.println("Error al actualizar el jefe: ID no válido");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el jefe: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
    }

    @Override
    public boolean delete(int idJefe) throws SQLException {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_DeleteJefeCuadrilla(?)}";
            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idJefe);

            int rowsAffected = conn.comando.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Jefe eliminado con ID: " + idJefe);
                return true;
            } else {
                System.err.println("Error al eliminar el jefe: ID no válido");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el jefe: ");
            e.printStackTrace();
            return false;
        } finally {
            conn.closeConnection();
        }

    }

    @Override
    public List<Jefe> getAllJefesCuadrilla() throws SQLException {
        Conexion conn = new Conexion();
        List<Jefe> jefes = new ArrayList<>();

        try {
            String call = "{call sp_GetAllJefesCuadrilla()}";
            conn.comando = conn.cnx.prepareCall(call);

            ResultSet rs = conn.comando.executeQuery();

            while(rs.next()) {
                Jefe jefe = new Jefe(
                    rs.getInt("usuario_id"),
                    rs.getString("Nombre"),
                    SHA1.encryptThisString(rs.getString("contrasenia")),
                    rs.getString("rol"),
                    rs.getString("telefono")
                );
                jefes.add(jefe);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todos los jefes: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return jefes;
    }

    public void asignarJefeACuadrilla(int idJefe, int idCuadrilla){
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_AsignarJefeACuadrilla(?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, idJefe);
            conn.comando.setInt(2, idCuadrilla);

            int rowsAffected = conn.comando.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Jefe asignado a la cuadrilla con ID: " + idCuadrilla);
            } else {
                System.err.println("Error al asignar jefe a la cuadrilla: ID no válido");
            }

        } catch (SQLException e) {
            System.out.println("Error al asignar jefe a la cuadrilla: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
    }

    public void removerJefeDeCuadrilla(int idCuadrilla) {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_RemoverJefeDeCuadrilla(?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, idCuadrilla);

            int rowsAffected = conn.comando.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Jefe removido de la cuadrilla con ID: " + idCuadrilla);
            } else {
                System.err.println("Error al remover jefe de la cuadrilla: ID no válido");
            }
        } catch (SQLException e) {
            System.out.println("Error al remover jefe de la cuadrilla: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
    }

}
