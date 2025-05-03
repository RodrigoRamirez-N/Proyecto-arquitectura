package dao.implementaciones;

import dao.interfaces.EmpleadoDAO;
import model.Empleado;
import java.util.List;
import util.SHA1;

import javax.swing.JOptionPane;

import util.Conexion;
import java.sql.*;
import java.util.ArrayList;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public int create(Empleado empleado) throws SQLException {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_CreateEmpleado(?,?,?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setString(1, empleado.getNombre());
            conn.comando.setString(2, empleado.getPassword());
            conn.comando.setString(3, empleado.getTelefono());
            conn.comando.registerOutParameter(4, Types.INTEGER);

            conn.comando.execute();

            int resultId = conn.comando.getInt(4);
            if(resultId > 0) {
                System.out.println("Empleado creado con ID: " + resultId);
                return resultId; // Retorna el empleado creado
            } else {
                System.err.println("(ifStmt)Error al crear empleado: ID no válido");
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("(catch)Error al crear empleado: ");
            e.printStackTrace();
            return -1;
        } finally {
            conn.closeConnection();
        }
    }

    @Override
    public Empleado read(int idEmpleado) throws SQLException {
        Conexion conn = new Conexion();
        Empleado empleado = null;

        try{
            String call = "{call sp_GetEmpleadoById(?)}";
            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idEmpleado);

            ResultSet rs = conn.executeResultSet();

            if(rs.next()){
                empleado = new Empleado(
                    rs.getInt("usuario_id"),
                    rs.getString("nombre"),
                    SHA1.encryptThisString(rs.getString("contrasenia")),
                    rs.getString("rol"),
                    rs.getString("telefono")
                );

            } else {
                System.out.println("No se encontró el empleado con ID: " + idEmpleado);
                JOptionPane.showMessageDialog(null, "El empleado con ID: " + idEmpleado +" no existe.", "Advertencia", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer el empleado: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return empleado;
    }

    @Override
    public void update(Empleado empleado) throws SQLException {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_UpdateEmpleado(?,?,?,?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, empleado.getId()); // no es para cambiar el id, es para buscar el empleado
            conn.comando.setString(2, empleado.getTelefono());
            conn.comando.setString(3, empleado.getNombre());
            conn.comando.setString(4, empleado.getPassword());
            conn.comando.registerOutParameter(5, Types.INTEGER);
            conn.comando.executeUpdate();

            int rowsAffected = conn.comando.getInt(5);

            if(rowsAffected > 0) {
                System.out.println("Empleado actualizado con ID: " + empleado.getId());
            } else {
                System.err.println("Empleado con ID válido, pero sin cambios.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el empleado: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
    }

    @Override
    public boolean delete(int idEmpleado) throws SQLException {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_DeleteEmpleado(?,?)}";
            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idEmpleado);
            conn.comando.registerOutParameter(2, Types.INTEGER);
            conn.comando.executeUpdate();
            int rowsAffected = conn.comando.getInt(2);

            if(rowsAffected > 0) {
                System.out.println("Empleado eliminado con ID: " + idEmpleado);
                return true;
            } else {
                System.err.println("No se eliminó ningún registro. Puede que el ID no exista o ya haya sido eliminado.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el empleado: ");
            e.printStackTrace();
            return false;
        } finally {
            conn.closeConnection();
        }
    }

    @Override
    public List<Empleado> getAllEmpleados() throws SQLException {
        Conexion conn = new Conexion();
        List<Empleado> empleados = new ArrayList<>();

        try {
            String call = "{call sp_GetAllEmpleados()}";
            conn.comando = conn.cnx.prepareCall(call);

            ResultSet rs = conn.comando.executeQuery();

            while(rs.next()) {
                Empleado empleado = new Empleado(
                    rs.getInt("usuario_id"),
                    rs.getString("Nombre"),
                    SHA1.encryptThisString(rs.getString("contrasenia")),
                    rs.getString("rol"),
                    rs.getString("telefono")
                );
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener todos los empleados: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return empleados;
    }

    @Override
    public void asignarEmpleadoACuadrilla(int idEmpleado, int idCuadrilla) throws SQLException {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_AsignarEmpleadoACuadrilla(?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, idEmpleado);
            conn.comando.setInt(2, idCuadrilla);

            int rowsAffected = conn.comando.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Empleado asignado a la cuadrilla con ID: " + idCuadrilla);
            } else {
                System.err.println("Error al asignar el empleado a la cuadrilla: ID no válido");
            }

        } catch (SQLException e) {
            System.out.println("Error al asignar el empleado a la cuadrilla: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
    }

    @Override
    public void removerEmpleadoDeCuadrilla(int idEmpleado, int idCuadrilla) {
        Conexion conn = new Conexion();

        try {
            String call = "{call sp_RemoverEmpleadoDeCuadrilla}";

            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idEmpleado);
            conn.comando.setInt(2, idCuadrilla);

            int rowsAffected = conn.comando.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Empleado removido de la cuadrilla con ID: " + idCuadrilla);
            } else {
                System.err.println("Error al remover el empleado de la cuadrilla: ID no válido");
            }
            
        } catch (SQLException e) {
            System.out.println("Error al remover el empleado de la cuadrilla: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
    }

    @Override   
    public List<Empleado> getEmpleadosByCuadrilla(int idCuadrilla) throws SQLException {
        Conexion conn = new Conexion();
        List<Empleado> empleados = new ArrayList<>();

        try {
            String call = "{call sp_ObtenerEmpleadosPorCuadrilla(?)}";
            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idCuadrilla);

            ResultSet rs = conn.comando.executeQuery();

            while(rs.next()) {
                Empleado empleado = new Empleado(
                    rs.getInt("empleado_id"),
                    rs.getString("NombreEmpleado"),
                    SHA1.encryptThisString(rs.getString("contrasenia")),
                    rs.getString("rol"),
                    rs.getString("telefono")
                );
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los empleados de la cuadrilla: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return empleados;
    }

}
