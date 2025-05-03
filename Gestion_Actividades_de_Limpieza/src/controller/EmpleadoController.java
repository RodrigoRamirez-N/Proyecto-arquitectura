package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.implementaciones.EmpleadoDAOImpl;
import dao.interfaces.EmpleadoDAO;
import model.Empleado;

public class EmpleadoController {

    private EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    // Aquí puedes implementar los métodos para manejar la lógica de negocio relacionada con los empleados
    // Aqui es donde se realizan validaciones o reglas de negocio antes de llamar al DAO
    // Igual es posible manejar algunas validaciones en la vista o en el DAO, pero es mejor hacerlo en el controlador
    // para mantener la logica de negocio separada de la logica de acceso a datos
    // en el dao solo manejo excepciones referentes a las operaciones SQL

    private boolean validarEmpleadoFields(Empleado emp) {
        // validaciones, por ejemplo que el id no sea negativo
        if (emp.getId() < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo");
        }
        // validaciones, por ejemplo que el nombre no sea null o vacio
        if (emp.getNombre() == null || emp.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacio");
        }
        // validaciones, por ejemplo que el password no sea null o vacio
        if (emp.getPassword() == null || emp.getPassword().isEmpty()) {
            throw new IllegalArgumentException("El password no puede ser nulo o vacio");
        }
        // validaciones, por ejemplo que el telefono no sea null o vacio
        if (emp.getTelefono() == null || emp.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El telefono no puede ser nulo o vacio");
        }
        return true;
    }

    public int crearEmpleado(String nombre, String password, String telefono) {
        
        Empleado emp = new Empleado(0, nombre, password, "empleado", telefono);
        // el id se asigna automáticamente en la base de datos asi que se le asigna 0 al crear el objeto
        if (!validarEmpleadoFields(emp)) {
            throw new IllegalArgumentException("Los campos del empleado no son validos");
        }
        try {
            return empleadoDAO.create(emp);
        } catch (SQLException e) { 
            e.printStackTrace();
        }
        return -1; // Retorna null si hubo un error
    }
    
    public Empleado leerEmpleado(int idEmpleado) {
        if( idEmpleado < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo");
        }

        try {
            return empleadoDAO.read(idEmpleado);
            //System.out.println(emp.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si hubo un error
    }
    
    public void deleteEmpleado(int idEmpleado) {
        if( idEmpleado < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo");
        }
        try {
            empleadoDAO.delete(idEmpleado);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateEmpleado(int idEmpleado, String nombre, String password, String rol, String telefono) {
        if( idEmpleado < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo");
        }
        Empleado emp = new Empleado(idEmpleado, nombre, password, rol, telefono);
        if (!validarEmpleadoFields(emp)) {
            throw new IllegalArgumentException("Los campos del empleado no son validos");
        }
        try {
            empleadoDAO.update(emp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Empleado> getAllEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        try {
            empleados = empleadoDAO.getAllEmpleados();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    public List<Empleado> getEmpleadosByCuadrilla(int idCuadrilla) {
        List<Empleado> empleados = new ArrayList<>();
        try {
            empleados = empleadoDAO.getEmpleadosByCuadrilla(idCuadrilla);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    public void asignarEmpleadoACuadrilla(int idEmpleado, int idCuadrilla) {
        if( idEmpleado < 0 || idCuadrilla < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo");
        }
        try {
            empleadoDAO.asignarEmpleadoACuadrilla(idEmpleado, idCuadrilla);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removerEmpleadoDeCuadrilla(int idEmpleado, int idCuadrilla) {
        if( idEmpleado < 0 || idCuadrilla < 0) {
            throw new IllegalArgumentException("El id no puede ser negativo");
        }
        try {
            empleadoDAO.removerEmpleadoDeCuadrilla(idEmpleado, idCuadrilla);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
