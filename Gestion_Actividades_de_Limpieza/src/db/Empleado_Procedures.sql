-- CRUD para Empleado
USE GestionLimpieza;
DELIMITER //

DROP PROCEDURE IF EXISTS sp_CreateEmpleado;
DROP PROCEDURE IF EXISTS sp_GetEmpleadoById;
DROP PROCEDURE IF EXISTS sp_UpdateEmpleado;
DROP PROCEDURE IF EXISTS sp_GetAllEmpleados;
DROP PROCEDURE IF EXISTS sp_DeleteEmpleado;


CREATE PROCEDURE sp_CreateEmpleado(
    IN p_nombre VARCHAR(255),
    IN p_contrasena VARCHAR(255),
    IN p_telefono VARCHAR(10),
    OUT p_empleado_id INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_empleado_id = -1;
    END;
    
    START TRANSACTION;
    
    INSERT INTO Usuario(Nombre, Contrasenia, rol)
    VALUES (p_nombre, p_contrasena, 'empleado');
    
    SET @usuario_id = LAST_INSERT_ID();
    
    INSERT INTO Empleado(empleado_id, telefono)
    VALUES (@usuario_id, p_telefono);
    
    SET p_empleado_id = @usuario_id;
    
    COMMIT;
END//

CREATE PROCEDURE sp_GetEmpleadoById(
    IN p_id INT
)
BEGIN
    SELECT u.*, e.telefono 
    FROM Usuario u
    INNER JOIN Empleado e ON u.usuario_id = e.empleado_id
    WHERE e.empleado_id = p_id;
END//

CREATE PROCEDURE sp_UpdateEmpleado(
    IN p_id INT,
    IN p_telefono VARCHAR(20),
    IN p_nombre VARCHAR(255),
    IN p_contrasena VARCHAR(255),
    OUT filas_afectadas INT
)
BEGIN
    DECLARE total INT DEFAULT 0;

    START TRANSACTION;

    UPDATE Usuario
    SET Nombre = p_nombre,
        Contrasenia = p_contrasena
    WHERE usuario_id = p_id;

    SET total = total + ROW_COUNT();

    UPDATE Empleado
    SET telefono = p_telefono
    WHERE empleado_id = p_id;

    SET total = total + ROW_COUNT();

    COMMIT;

    SET filas_afectadas = total;
END//

CREATE PROCEDURE sp_GetAllEmpleados()
BEGIN
    SELECT u.*, e.telefono 
    FROM Usuario u
    INNER JOIN Empleado e ON u.usuario_id = e.empleado_id;
END//


CREATE PROCEDURE sp_DeleteEmpleado(
    IN p_id INT,
    OUT filas_afectadas INT
)
BEGIN
    DECLARE total INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET filas_afectadas = -1;
    END;

    START TRANSACTION;

    DELETE FROM Empleado
    WHERE empleado_id = p_id;
    SET total = total + ROW_COUNT();

    DELETE FROM Usuario
    WHERE usuario_id = p_id;
    SET total = total + ROW_COUNT();

    COMMIT;

    SET filas_afectadas = total;
END //


DELIMITER ;