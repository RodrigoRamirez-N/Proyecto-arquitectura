USE GestionLimpieza;
DELIMITER //

DROP PROCEDURE IF EXISTS sp_DeleteActividad//
DROP PROCEDURE IF EXISTS sp_CreateActividad//
DROP PROCEDURE IF EXISTS sp_GetActividadById//
DROP PROCEDURE IF EXISTS sp_UpdateActividad//
DROP PROCEDURE IF EXISTS sp_GetAllActividades//
DROP PROCEDURE IF EXISTS sp_GetActividadesByColonia//
DROP PROCEDURE IF EXISTS sp_SubirEvidencia//
DROP PROCEDURE IF EXISTS sp_GetActividadesByCuadrilla//
DROP PROCEDURE IF EXISTS sp_GetActividadesByFecha//

CREATE PROCEDURE sp_DeleteActividad(
    IN p_actividad_id INT
)
BEGIN
    DELETE FROM Actividad WHERE actividad_id = p_actividad_id;
END//

CREATE PROCEDURE sp_CreateActividad(
    IN p_Descripcion VARCHAR(1000),
    IN p_Fecha DATE,
    IN p_imagenEvidencia LONGTEXT,
    IN p_estado ENUM('Pendiente', 'En Proceso', 'Finalizada'),
    IN p_cuadrilla_id INT,
    IN p_cve_colonia INT,
    IN p_usuario_registro_id INT,
    OUT p_actividad_id INT
)
BEGIN
    INSERT INTO Actividad(
        Descripcion, 
        Fecha, 
        imagenEvidencia, 
        estado, 
        cuadrilla_id, 
        cve_colonia, 
        usuario_registro_id
    )
    VALUES (
        p_Descripcion,
        p_Fecha,
        p_imagenEvidencia,
        p_estado,
        p_cuadrilla_id,
        p_cve_colonia,
        p_usuario_registro_id
    );
    
    SET p_actividad_id = LAST_INSERT_ID();
END//

CREATE PROCEDURE sp_GetActividadById(
    IN p_id INT
)
BEGIN
    SELECT a.*, c.NombreColonia, cu.NombreCuadrilla 
    FROM Actividad a
    LEFT JOIN Colonia c ON a.cve_colonia = c.cve_colonia
    LEFT JOIN Cuadrilla cu ON a.cuadrilla_id = cu.cuadrilla_id
    WHERE a.actividad_id = p_id;
END//

CREATE PROCEDURE sp_UpdateActividad(
    IN p_id INT,
    IN p_Descripcion VARCHAR(1000),
    IN p_Fecha DATE,
    IN p_imagenEvidencia LONGTEXT,
    IN p_estado ENUM('Pendiente', 'En Proceso', 'Finalizada'),
    IN p_cuadrilla_id INT,
    IN p_cve_colonia INT
)
BEGIN
    UPDATE Actividad
    SET Descripcion = p_Descripcion,
        Fecha = p_Fecha,
        imagenEvidencia = p_imagenEvidencia,
        estado = p_estado,
        cuadrilla_id = p_cuadrilla_id,
        cve_colonia = p_cve_colonia
    WHERE actividad_id = p_id;
END//

CREATE PROCEDURE sp_GetAllActividades()
BEGIN
    SELECT a.*, c.NombreColonia, cu.NombreCuadrilla 
    FROM Actividad a
    LEFT JOIN Colonia c ON a.cve_colonia = c.cve_colonia
    LEFT JOIN Cuadrilla cu ON a.cuadrilla_id = cu.cuadrilla_id;
END//

CREATE PROCEDURE sp_GetActividadesByColonia(
    IN p_idColonia INT
)
BEGIN
    SELECT * FROM Actividad 
    WHERE cve_colonia = p_idColonia;
END//

CREATE PROCEDURE sp_SubirEvidencia(
    IN p_idActividad INT,
    IN p_rutaImagen LONGTEXT
)
BEGIN
    UPDATE Actividad
    SET imagenEvidencia = p_rutaImagen
    WHERE actividad_id = p_idActividad;
END//

-- Obtener actividades por cuadrilla
CREATE PROCEDURE sp_GetActividadesByCuadrilla(
    IN p_idCuadrilla INT
)
BEGIN
    SELECT a.*, c.NombreColonia, cu.NombreCuadrilla 
    FROM Actividad a
    LEFT JOIN Colonia c ON a.cve_colonia = c.cve_colonia
    LEFT JOIN Cuadrilla cu ON a.cuadrilla_id = cu.cuadrilla_id
    WHERE a.cuadrilla_id = p_idCuadrilla;
END//

-- Obtener actividades por fecha (formato 'YYYY-MM-DD')
CREATE PROCEDURE sp_GetActividadesByFecha(
    IN p_fecha VARCHAR(10)
)
BEGIN
    SELECT a.*, c.NombreColonia, cu.NombreCuadrilla 
    FROM Actividad a
    LEFT JOIN Colonia c ON a.cve_colonia = c.cve_colonia
    LEFT JOIN Cuadrilla cu ON a.cuadrilla_id = cu.cuadrilla_id
    WHERE a.Fecha = STR_TO_DATE(p_fecha, '%Y-%m-%d');
END//

DELIMITER ;
