package controller;

import java.sql.SQLException;

import dao.interfaces.EmpleadoDAO;
import model.Empleado;

public class EmpleadoController {

    private final EmpleadoDAO empleadoDAO;

    public EmpleadoController(EmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    // Aquí puedes implementar los métodos para manejar la lógica de negocio relacionada con los empleados
    // Aqui es donde se realizan validaciones o reglas de negocio antes de llamar al DAO
    // Igual es posible manejar algunas validaciones en la vista o en el DAO, pero es mejor hacerlo en el controlador
    // para mantener la logica de negocio separada de la logica de acceso a datos
    // en el dao solo manejo excepciones referentes a las operaciones SQL

    public void crearEmpleado(String nombre, String password, String rol, String telefono) {
        // validaciones, por ejemplo que ningun campo sea null o vacio
        if (nombre == null || password == null || rol == null || telefono == null) {
            throw new IllegalArgumentException("Ningun campo puede ser nulo");
        }
        if (nombre.isEmpty() || password.isEmpty() || rol.isEmpty() || telefono.isEmpty()) {
            throw new IllegalArgumentException("Ningun campo puede estar vacio");
        }
        // para crear un empleado el rol debe ser de empleado unicamente
        if (!rol.equals("empleado")) {
            throw new IllegalArgumentException("El rol debe ser 'empleado'");
        }
        // para crear un empleado el telefono debe ser un numero de 10 digitos
        if (telefono.length() != 10) {
            throw new IllegalArgumentException("El telefono debe ser un numero de 10 digitos");
        }
        // para crear un empleado el password debe ser de al menos 8 caracteres
        if (password.length() < 8) {
            throw new IllegalArgumentException("El password debe ser de al menos 8 caracteres");
        }
        // para crear un empleado el nombre no debe tener caracteres especiales
        if (!nombre.matches("[a-zA-Z0-9 ]*")) {
            throw new IllegalArgumentException("El nombre no puede tener caracteres especiales");
        }
        // para crear un empleado el telefono no debe tener caracteres especiales
        if (!telefono.matches("[0-9]*")) {
            throw new IllegalArgumentException("El telefono no puede tener caracteres especiales");
        }
        
        Empleado emp = new Empleado(0, nombre, password, rol, telefono);
        // el id se asigna automáticamente en la base de datos asi que se le asigna 0 al crear el objeto

        try {
            empleadoDAO.create(emp);
        } catch (SQLException e) { 
            e.printStackTrace();
        }
    }
    
    public void leerEmpleado(int idEmpleado) {
        // Lógica para leer un empleado utilizando el DAO
    }
    
    // Otros métodos según sea necesario
    
}
