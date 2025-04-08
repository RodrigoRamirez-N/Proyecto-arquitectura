-- CRUD para Jefe de Cuadrilla
USE GestionLimpieza;
DELIMITER //

DROP PROCEDURE IF EXISTS sp_CreateJefeCuadrilla;
DROP PROCEDURE IF EXISTS sp_GetJefeCuadrillaById;
DROP PROCEDURE IF EXISTS sp_UpdateJefeCuadrilla;
DROP PROCEDURE IF EXISTS sp_GetAllJefesCuadrilla;
DROP PROCEDURE IF EXISTS sp_AsignarJefeACuadrilla;
DROP PROCEDURE IF EXISTS sp_RemoverJefeDeCuadrilla;

CREATE PROCEDURE sp_CreateJefeCuadrilla(
    IN p_nombre VARCHAR(255),
    IN p_contrasena VARCHAR(255),
    IN p_telefono VARCHAR(10),
    OUT p_jefe_id INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_jefe_id = -1;
    END;
    
    START TRANSACTION;
    
    INSERT INTO Usuario(Nombre, Contrasenia, rol)
    VALUES (p_nombre, p_contrasena, 'jefe');
    
    SET @usuario_id = LAST_INSERT_ID();
    
    INSERT INTO Jefe_Cuadrilla(jefe_cuadrilla_id, telefono)
    VALUES (@usuario_id, p_telefono);
    
    SET p_jefe_id = @usuario_id;
    
    COMMIT;
END//

CREATE PROCEDURE sp_GetJefeCuadrillaById(
    IN p_id INT
)
BEGIN
    SELECT u.*, j.telefono 
    FROM Usuario u
    INNER JOIN Jefe_Cuadrilla j ON u.usuario_id = j.jefe_cuadrilla_id
    WHERE j.jefe_cuadrilla_id = p_id;
END//

CREATE PROCEDURE sp_UpdateJefeCuadrilla(
    IN p_id INT,
    IN p_telefono VARCHAR(10),
    IN p_nombre VARCHAR(255),
    IN p_contrasena VARCHAR(255)
)
BEGIN
    START TRANSACTION;
    
    UPDATE Usuario
    SET Nombre = p_nombre,
        Contrasenia = p_contrasena
    WHERE usuario_id = p_id;
    
    UPDATE Jefe_Cuadrilla
    SET telefono = p_telefono
    WHERE jefe_cuadrilla_id = p_id;
    
    COMMIT;
END//

CREATE PROCEDURE sp_GetAllJefesCuadrilla()
BEGIN
    SELECT u.usuario_id, u.Nombre, j.telefono 
    FROM Usuario u
    INNER JOIN Jefe_Cuadrilla j ON u.usuario_id = j.jefe_cuadrilla_id;
END//

DROP PROCEDURE IF EXISTS sp_AsignarJefeACuadrilla;
CREATE PROCEDURE sp_AsignarJefeACuadrilla (
    IN p_jefe_id INT,
    IN p_cuadrilla_id INT
)
BEGIN
    -- Validar existencia del jefe
    IF NOT EXISTS (SELECT 1 FROM Jefe_Cuadrilla WHERE jefe_cuadrilla_id = p_jefe_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El jefe no existe';
    END IF;

    -- Validar existencia de la cuadrilla
    IF NOT EXISTS (SELECT 1 FROM Cuadrilla WHERE cuadrilla_id = p_cuadrilla_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cuadrilla no existe';
    END IF;

    -- Asignar el jefe
    UPDATE Cuadrilla
    SET jefe_id = p_jefe_id
    WHERE cuadrilla_id = p_cuadrilla_id;
END //

DROP PROCEDURE IF EXISTS sp_RemoverJefeDeCuadrilla;
CREATE PROCEDURE sp_RemoverJefeDeCuadrilla (
    IN p_cuadrilla_id INT
)
BEGIN
    -- Validar existencia de la cuadrilla
    IF NOT EXISTS (SELECT 1 FROM Cuadrilla WHERE cuadrilla_id = p_cuadrilla_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cuadrilla no existe';
    END IF;

    -- Quitar el jefe (setear a NULL)
    UPDATE Cuadrilla
    SET jefe_id = NULL
    WHERE cuadrilla_id = p_cuadrilla_id;
END //

DROP PROCEDURE IF EXISTS sp_DeleteJefeCuadrilla;
CREATE PROCEDURE sp_DeleteJefeCuadrilla(
    IN p_id INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;
    
    START TRANSACTION;
    
    DELETE FROM Jefe_Cuadrilla WHERE jefe_cuadrilla_id = p_id;
    DELETE FROM Usuario WHERE usuario_id = p_id;
    
    COMMIT;
END//

DELIMITER ;