USE GestionLimpieza;

DROP PROCEDURE IF EXISTS sp_CreateColonia;
DROP PROCEDURE IF EXISTS sp_GetColoniaById;
DROP PROCEDURE IF EXISTS sp_UpdateColonia;
DROP PROCEDURE IF EXISTS sp_DeleteColonia;
DROP PROCEDURE IF EXISTS sp_GetAllColonias;

DELIMITER //

CREATE PROCEDURE sp_CreateColonia(
    IN p_nombre VARCHAR(255),
    OUT p_id_colonia INT
)
BEGIN
    INSERT INTO Colonia(NombreColonia) 
    VALUES (p_nombre);
    
    SET p_id_colonia = LAST_INSERT_ID(); -- Retorna el ID generado
END//

CREATE PROCEDURE sp_GetColoniaById(
    IN p_id INT
)
BEGIN
    SELECT * FROM Colonia WHERE cve_colonia = p_id;
END//

CREATE PROCEDURE sp_UpdateColonia(
    IN p_cve_colonia INT,
    IN p_NombreColonia VARCHAR(255)
)
BEGIN
    UPDATE Colonia
    SET NombreColonia = p_NombreColonia
    WHERE cve_colonia = p_cve_colonia;
END//

CREATE PROCEDURE sp_DeleteColonia(
    IN p_id INT
)
BEGIN
    DELETE FROM Colonia WHERE cve_colonia = p_id;
END//

CREATE PROCEDURE sp_GetAllColonias()
BEGIN
    SELECT * FROM Colonia ORDER BY NombreColonia;
END//

DELIMITER ;