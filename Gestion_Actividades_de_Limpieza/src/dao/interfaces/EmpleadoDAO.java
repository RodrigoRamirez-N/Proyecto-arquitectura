package dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import model.Empleado;

public interface EmpleadoDAO {

    int createEmpleado(Empleado empleado) throws SQLException;
    Empleado readEmpleado(int idEmpleado) throws SQLException;
    Empleado updateEmpleado(Empleado empleado) throws SQLException;
    boolean deleteEmpleado(int idEmpleado) throws SQLException;

    List<Empleado> getAllEmpleados() throws SQLException;
    void asignarEmpleadoACuadrilla(int idEmpleado, int idCuadrilla) throws SQLException; // asignar empleado a cuadrilla
    void removerEmpleadoDeCuadrilla(int idEmpleado, int idCuadrilla) throws SQLException; // remover empleado de cuadrilla
    List<Empleado> getEmpleadosByCuadrilla(int idCuadrilla) throws SQLException; // obtener empleados por cuadrilla    
}