DELIMITER //

CREATE PROCEDURE sp_AsignarEmpleadoACuadrilla (
    IN p_empleado_id INT,
    IN p_cuadrilla_id INT
)
BEGIN
    -- Validar existencia del empleado
    IF NOT EXISTS (SELECT 1 FROM Empleado WHERE empleado_id = p_empleado_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El empleado no existe';
    END IF;

    -- Validar existencia de la cuadrilla
    IF NOT EXISTS (SELECT 1 FROM Cuadrilla WHERE cuadrilla_id = p_cuadrilla_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cuadrilla no existe';
    END IF;

    -- Verificar si ya está asignado
    IF EXISTS (
        SELECT 1 FROM Empleado_Cuadrilla 
        WHERE empleado_id = p_empleado_id AND cuadrilla_id = p_cuadrilla_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El empleado ya está asignado a esta cuadrilla';
    END IF;

    -- Insertar la asignación
    INSERT INTO Empleado_Cuadrilla (empleado_id, cuadrilla_id)
    VALUES (p_empleado_id, p_cuadrilla_id);
END //

DELIMITER ;


DELIMITER //

CREATE PROCEDURE sp_RemoverEmpleadoDeCuadrilla (
    IN p_empleado_id INT,
    IN p_cuadrilla_id INT
)
BEGIN
    -- Validar existencia de la asignación
    IF NOT EXISTS (
        SELECT 1 FROM Empleado_Cuadrilla 
        WHERE empleado_id = p_empleado_id AND cuadrilla_id = p_cuadrilla_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El empleado no está asignado a esta cuadrilla';
    END IF;

    -- Eliminar la relación
    DELETE FROM Empleado_Cuadrilla
    WHERE empleado_id = p_empleado_id AND cuadrilla_id = p_cuadrilla_id;
END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE sp_ObtenerEmpleadosPorCuadrilla (
    IN p_cuadrilla_id INT
)
BEGIN
    -- Validar existencia de la cuadrilla
    IF NOT EXISTS (
        SELECT 1 FROM Cuadrilla WHERE cuadrilla_id = p_cuadrilla_id
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cuadrilla no existe';
    END IF;

    -- Obtener datos de cuadrilla y empleados
    SELECT 
        c.cuadrilla_id,
        c.NombreCuadrilla,
        jefe.Nombre AS NombreJefe,
        u.usuario_id AS EmpleadoID,
        u.Nombre AS NombreEmpleado,
        u.rol,
        e.telefono
    FROM Cuadrilla c
    JOIN Jefe_Cuadrilla jc ON c.jefe_id = jc.jefe_cuadrilla_id
    JOIN Usuario jefe ON jc.jefe_cuadrilla_id = jefe.usuario_id
    JOIN Empleado_Cuadrilla ec ON c.cuadrilla_id = ec.cuadrilla_id
    JOIN Empleado e ON ec.empleado_id = e.empleado_id
    JOIN Usuario u ON e.empleado_id = u.usuario_id
    WHERE c.cuadrilla_id = p_cuadrilla_id;
END //

DELIMITER ;
