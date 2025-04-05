USE GestionLimpieza;
DELIMITER //

CREATE PROCEDURE sp_CreateColonia(
    IN p_cve_colonia INT,
    IN p_NombreColonia VARCHAR(255),
    OUT p_resultado INT
)
BEGIN
    INSERT INTO Colonia(cve_colonia, NombreColonia)
    VALUES (p_cve_colonia, p_NombreColonia);
    
    SET p_resultado = p_cve_colonia;
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

CREATE PROCEDURE sp_GetColoniasByNombre(
    IN p_nombre VARCHAR(255)
)
BEGIN
    SELECT * FROM Colonia 
    WHERE NombreColonia LIKE CONCAT('%', p_nombre, '%');
END//

DELIMITER ;