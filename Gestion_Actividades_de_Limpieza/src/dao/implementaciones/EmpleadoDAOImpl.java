package dao.implementaciones;

import dao.interfaces.EmpleadoDAO;
import model.Empleado;
import model.Usuario;
import java.util.List;
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
            conn.comando.setString(2, empleado.getContrasena());
            conn.comando.setString(3, empleado.getTelefono());
            conn.comando.registerOutParameter(4, Types.INTEGER);

            conn.comando.execute();

            int resultId = conn.comando.getInt(4);
            if(resultId > 0) {
                System.out.println("Empleado creado con ID: " + resultId);
                return resultId;
            } else {
                System.err.println("(ifStmt)Error al crear empleado: ID no válido");
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("(catch)Error al crear empleado: ");
            e.printStackTrace();
            return -1;
        } finally {
            conn.cerrarConexion();
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

            ResultSet rs = conn.comando.executeQuery();

            if(rs.next()){
                empleado = new Empleado();
                empleado.setId(rs.getInt("empleado_id")); //method setId comes from user class
                empleado.setNombre(rs.getString("nombre")); //method comes from user
                empleado.setPassword(rs.getString("contrasena").hashCode()); // Hashing the password also comes from user class
                empleado.setTelefono(rs.getString("telefono")); // method comes from empleado
            } else {
                System.out.println("No se encontró el empleado con ID: " + idEmpleado);
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
            String call = "{call sp_UpdateEmpleado(?,?,?,?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, empleado.getId()); // no es para cambiar el id, es para buscar el empleado
            conn.comando.setString(2, empleado.getNombre());
            conn.comando.setString(3, empleado.getPassword());
            conn.comando.setString(4, empleado.getTelefono());

            int rowsAffected = conn.comando.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Empleado actualizado con ID: " + empleado.getId());
                return empleado;
            } else {
                System.err.println("Error al actualizar el empleado: ID no válido");
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
            String call = "{call sp_DeleteEmpleado(?)}";
            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idEmpleado);

            int rowsAffected = conn.comando.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Empleado eliminado con ID: " + idEmpleado);
                return true;
            } else {
                System.err.println("Error al eliminar el empleado: ID no válido");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el empleado: ");
            e.printStackTrace();
            return false;
        } finally {
            conn.closeConnection();
        }
        return true;
    }

    @Override
    public List<Empleado> getAll() throws SQLException {
        Conexion conn = new Conexion();
        List<Empleado> empleados = new ArrayList<>();

        try {
            String call = "{call sp_GetAllEmpleados()}";
            conn.comando = conn.cnx.prepareCall(call);

            ResultSet rs = conn.comando.executeQuery();

            while(rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(rs.getInt("empleado_id"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setPassword(rs.getString("contrasena").hashCode());
                empleado.setTelefono(rs.getString("telefono"));

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
                Empleado empleado = new Empleado();
                empleado.setId(rs.getInt("empleado_id"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setPassword(rs.getString("contrasena").hashCode());
                empleado.setTelefono(rs.getString("telefono"));

                empleados.add(empleado);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los empleados de la cuadrilla: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }

    }

}
