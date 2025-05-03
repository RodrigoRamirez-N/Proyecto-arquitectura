CREATE DATABASE GestionLimpieza;

USE GestionLimpieza;
CALL sp_CreateEmpleado('Pepin', 'Password123', '1234567890', @empleado_id);
CALL sp_CreateEmpleado('Tacochino', 'Contraseña', '8441405182', @empleado_id);
CALL sp_CreateEmpleado('Empleado3', 'bruneichon', '8441346985', @empleado_id);

USE GestionLimpieza;
CALL sp_DeleteEmpleado(2);

USE GestionLimpieza;
CALL sp_GetAllEmpleados();

USE GestionLimpieza;
CALL sp_CreateJefeCuadrilla('Jefe1', 'Password123', '1234567890', @jefe_id);

USE GestionLimpieza;
SELECT * FROM Jefe_Cuadrilla;

USE GestionLimpieza;
CALL sp_GetJefeCuadrillaById(4);

USE GestionLimpieza;
CALL sp_CreateCuadrilla('Cuadrilla1', null, @cuadrilla_id);

USE GestionLimpieza;
CALL sp_GetCuadrillaById(1);

USE GestionLimpieza;
CALL sp_GetAllCuadrillas();

USE GestionLimpieza;
CALL sp_AsignarEmpleadoACuadrilla(1, 1);
CALL sp_AsignarEmpleadoACuadrilla(2, 1);
CALL sp_AsignarEmpleadoACuadrilla(3, 1);

USE GestionLimpieza;
CALL sp_ObtenerEmpleadosPorCuadrilla(1);

USE GestionLimpieza;
SELECT * FROM Empleado_Cuadrilla WHERE cuadrilla_id = 1;

--obtiene primero el id del jefe de la cuadrilla luego el id de la cuadrilla
USE GestionLimpieza;
CALL sp_AsignarJefeACuadrilla(4, 1);
CALL sp_GetCuadrillaById(1);

-- poblada la tabla colonia
USE GestionLimpieza;
SELECT * FROM Colonia;

Use GestionLimpieza;
SELECT * FROM Usuario;

Use GestionLimpieza;
CALL sp_ObtenerEmpleadosPorCuadrilla(1);

USE GestionLimpieza;
CALL sp_GetColoniasPorTipoActividad('Limpieza de alcantarillas');

USE GestionLimpieza;
DELETE FROM Colonia;
DELETE FROM Cuadrilla;
DELETE FROM Jefe_Cuadrilla;
DELETE FROM Empleado_Cuadrilla;
DELETE FROM Empleado;
DELETE FROM Actividad;
DELETE FROM Usuario;