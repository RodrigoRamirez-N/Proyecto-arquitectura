-- CRUD para Jefe de Cuadrilla
USE GestionLimpieza;
DELIMITER //

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

DELIMITER ;