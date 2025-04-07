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
    
}