USE GestionLimpieza;
DELIMITER //

DROP PROCEDURE IF EXISTS sp_CreateCuadrilla;
CREATE PROCEDURE sp_CreateCuadrilla(
    IN p_NombreCuadrilla VARCHAR(255),
    IN p_jefe_id INT,
    IN p_fecha DATE,
    OUT p_cuadrilla_id INT
)
BEGIN
    INSERT INTO Cuadrilla(NombreCuadrilla, jefe_id, Fecha)
    VALUES (
        p_NombreCuadrilla,
        p_jefe_id,
        COALESCE(p_fecha, CURDATE())
    );

    SET p_cuadrilla_id = LAST_INSERT_ID();
END//

DROP PROCEDURE IF EXISTS sp_GetCuadrillaById;
CREATE PROCEDURE sp_GetCuadrillaById(
    IN p_id INT
)
BEGIN
    SELECT 
        c.*,
        u.Nombre AS nombre_jefe 
    FROM Cuadrilla c
    LEFT JOIN Jefe_Cuadrilla j ON c.jefe_id = j.jefe_cuadrilla_id
    LEFT JOIN Usuario u ON j.jefe_cuadrilla_id = u.usuario_id
    WHERE c.cuadrilla_id = p_id;
END//

DROP PROCEDURE IF EXISTS sp_UpdateCuadrilla;
CREATE PROCEDURE sp_UpdateCuadrilla(
    IN p_cuadrilla_id INT,
    IN p_NombreCuadrilla VARCHAR(255),
    IN p_jefe_id INT
)
BEGIN
    UPDATE Cuadrilla
    SET NombreCuadrilla = p_NombreCuadrilla,
        jefe_id = p_jefe_id
    WHERE cuadrilla_id = p_cuadrilla_id;
END//

DROP PROCEDURE IF EXISTS sp_DeleteCuadrilla;
CREATE PROCEDURE sp_DeleteCuadrilla(
    IN p_id INT
)
BEGIN
    DELETE FROM Cuadrilla WHERE cuadrilla_id = p_id;
END//

DROP PROCEDURE IF EXISTS sp_GetAllCuadrillas;
CREATE PROCEDURE sp_GetAllCuadrillas()
BEGIN
    SELECT 
        c.*,
        u.Nombre AS nombre_jefe 
    FROM Cuadrilla c
    LEFT JOIN Jefe_Cuadrilla j ON c.jefe_id = j.jefe_cuadrilla_id
    LEFT JOIN Usuario u ON j.jefe_cuadrilla_id = u.usuario_id;
END//

-- Obtener cuadrillas por ID de jefe
DROP PROCEDURE IF EXISTS sp_GetCuadrillasByJefe;
CREATE PROCEDURE sp_GetCuadrillasByJefe(
    IN p_jefe_id INT
)
BEGIN
    SELECT c.*, u.Nombre AS nombre_jefe 
    FROM Cuadrilla c
    INNER JOIN Jefe_Cuadrilla j ON c.jefe_id = j.jefe_cuadrilla_id
    INNER JOIN Usuario u ON j.jefe_cuadrilla_id = u.usuario_id
    WHERE c.jefe_id = p_jefe_id;
END//

DELIMITER ;