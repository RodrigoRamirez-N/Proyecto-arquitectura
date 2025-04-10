import java.util.Date;
import controller.*;
import dao.implementaciones.*;
import dao.interfaces.*;
import model.*;

public class Demo {

    public static void main(String[] args) {
        // 1. AuthController Demo
        AuthController authController = new AuthController();
        System.out.println("=== Autenticación ===");
        authController.crearAdministrador("admin", "secret123");
        boolean loginExitoso = authController.autenticarUsuario("admin", "secret123");
        System.out.println("Login exitoso: " + loginExitoso + "\n");

        // 2. ColoniaController Demo
        ColoniaController coloniaController = new ColoniaController();
        System.out.println("=== Colonias ===");
        int idColonia = coloniaController.crearColonia("Las Flores de café tacvba");
        System.out.println("Colonia creada ID: " + idColonia + "\n");
        System.out.println("Detalles colonia: " + coloniaController.obtenerColoniaPorId(idColonia) + "\n");

        // 3. JefeController Demo
        JefeController jefeController = new JefeController();
        System.out.println("=== Jefes ===");
        int idJefe = jefeController.crearJefe("Juan Pérez", "juan123", "555-1234");
        System.out.println("Jefe creado ID: " + idJefe + "\n");

        // 4. CuadrillaController Demo
        CuadrillaController cuadrillaController = new CuadrillaController();
        System.out.println("=== Cuadrillas ===");
        Cuadrilla cuadrilla = cuadrillaController.crearCuadrilla("Cuadrilla Alpha", idJefe);
        System.out.println("Cuadrilla creada: " + cuadrilla.getIdCuadrilla());
        System.out.println("Fecha creación cuadrilla: " + cuadrilla.getFechaCreacionCuadrilla() + "\n");

        // 5. EmpleadoController Demo (requiere inyección de DAO)
        EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl(); 
        EmpleadoController empleadoController = new EmpleadoController(empleadoDAO);
        System.out.println("=== Empleados ===");
        int idEmpleado = empleadoController.crearEmpleado("Pedro Gómez", "pedro456", "limpieza", "555-5678");
        empleadoController.asignarEmpleadoACuadrilla(idEmpleado, cuadrilla.getIdCuadrilla());
        System.out.println("Empleado asignado a cuadrilla\n");

        // 6. ActividadController Demo
        ActividadController actividadController = new ActividadController();
        System.out.println("=== Actividades ===");
        Actividad actividad = actividadController.createActividad(
            "Limpieza de parque", 
            new Date(), 
            "http://evidencia.com/1", 
            "Pendiente", 
            cuadrilla.getIdCuadrilla(), 
            idColonia, 
            idJefe
        );
        System.out.println("Actividad creada ID: " + actividad.getActividad_id());
        System.out.println("Estado actividad: " + actividad.getEstado() + "\n");
        System.out.println("Detalles actividad: " + actividadController.getActividadById(actividad.getActividad_id()) + "\n");

        // Actualizar estado actividad
        System.out.println("=== Actualización ===");
        
        Actividad actividadActualizada = actividadController.updateActividad(
            actividad.getActividad_id(), 
            "Limpieza de parque", 
            new Date(), 
            "http://evidencia.com/1-completado", 
            "En proceso", 
            cuadrilla.getIdCuadrilla(), 
            idColonia,
            idJefe
        );

        if (actividadActualizada != null) {
            System.out.println("Estado actualizado: " + actividadActualizada.getEstado());
        } else {
            System.out.println("Error: La actividad no se pudo actualizar.");
        }
    }
}