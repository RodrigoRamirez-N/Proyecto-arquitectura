package view;

import java.util.Date;
import java.util.Random;

import controller.ActividadController;
import controller.ColoniaController;
import controller.CuadrillaController;
import controller.EmpleadoController;
import controller.JefeController;
import model.Actividad;
import model.Cuadrilla;

public class Demo2 {

    public static void main(String[] args) {
        // ya esta poblada la tabla colonias    
        CuadrillaController cuadrillaController = new CuadrillaController();
        JefeController jefeController = new JefeController();
        EmpleadoController empleadoController = new EmpleadoController();

        // Crear un nuevo jefe
        int idJefe = jefeController.crearJefe("Juan Pérez", "juan123", "555-1234");

        // crear empleados
        int idEmpleado1 = empleadoController.crearEmpleado("Pedro PRIMERO", "1", "limpieza", "555-5678");
        int idEmpleado2 = empleadoController.crearEmpleado("Pedro SEGUNDO", "2", "limpieza", "555-5678");
        int idEmpleado3 = empleadoController.crearEmpleado("Pedro TERCERO", "3", "limpieza", "555-5678");

        // Crear una nueva cuadrilla y asignar el jefe
        Cuadrilla cuad = cuadrillaController.crearCuadrilla("Cuadrilla raíz negativa", idJefe);
        empleadoController.asignarEmpleadoACuadrilla(idEmpleado1, cuad.getIdCuadrilla());
        empleadoController.asignarEmpleadoACuadrilla(idEmpleado2, cuad.getIdCuadrilla());
        empleadoController.asignarEmpleadoACuadrilla(idEmpleado3, cuad.getIdCuadrilla());
        jefeController.asignarJefeACuadrilla(idJefe, cuad.getIdCuadrilla());
        // Mostrar detalles de la cuadrilla
        System.out.println("Detalles de la cuadrilla: " + cuad.toString());

        System.out.println("Detalles del jefe: " + jefeController.obtenerJefe(idJefe).toString());

        empleadoController.getEmpleadosByCuadrilla(cuad.getIdCuadrilla()).forEach(empleado -> {
            System.out.println("Detalles del empleado: " + empleado.toString());
        });
        
        // Crear actividades en multiples colonias
        
        ActividadController actividadController = new ActividadController();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int idColonia = rand.nextInt(1, 764); // Genera un id de colonia aleatorio
            Actividad act = actividadController.createActividad(
                "Actividad " + (i + 1),
                "Limpieza de alcantarillas",
                new Date(),
                "http://evidencia.com/actividad" + (i + 1),
                "En Proceso",
                cuad.getIdCuadrilla(),
                idColonia,
                idJefe
            );
            System.out.println("Actividad creada ID: " + act.getActividad_id());
        }
        actividadController.getAllActividades().forEach(actividad -> {
            System.out.println("Detalles de la actividad: " + actividad.toString());
        });

        ColoniaController coloniaController = new ColoniaController();
        // Listar todas las colonias donde se ha realizado la actividad generada 
        System.out.println("Colonias donde se ha realizado la actividad de limpieza de alcantarillas:");
        coloniaController.getColoniasPorTipoActividad("Limpieza de alcantarillas").forEach(colonia -> {
            System.out.println("Detalles de la colonia: " + colonia.toString());
        });
    }

}
