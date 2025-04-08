package dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import model.Empleado;

public interface EmpleadoDAO {

    int create(Empleado empleado) throws SQLException;
    Empleado read(int idEmpleado) throws SQLException;
    void update(Empleado empleado) throws SQLException;
    boolean delete(int idEmpleado) throws SQLException;

    List<Empleado> getAllEmpleados() throws SQLException;
    void asignarEmpleadoACuadrilla(int idEmpleado, int idCuadrilla) throws SQLException; // asignar empleado a cuadrilla
    void removerEmpleadoDeCuadrilla(int idEmpleado, int idCuadrilla) throws SQLException; // remover empleado de cuadrilla
    List<Empleado> getEmpleadosByCuadrilla(int idCuadrilla) throws SQLException; // obtener empleados por cuadrilla    
}