-- CRUD Completo para Usuario
USE GestionLimpieza;
DELIMITER //

DROP PROCEDURE IF EXISTS sp_CrearUsuario;
DROP PROCEDURE IF EXISTS sp_ObtenerUsuario;
DROP PROCEDURE IF EXISTS sp_ActualizarUsuario;
DROP PROCEDURE IF EXISTS sp_EliminarUsuario;
DROP PROCEDURE IF EXISTS sp_AutenticarUsuario;

-- Crear
CREATE PROCEDURE sp_CrearUsuario(
    IN p_Nombre VARCHAR(255),
    IN p_Contrasenia VARCHAR(255),
    IN p_rol ENUM('admin', 'jefe', 'empleado'),
    OUT p_usuario_id INT
)
BEGIN
    INSERT INTO Usuario(Nombre, Contrasenia, rol)
    VALUES (p_Nombre, p_Contrasenia, p_rol);
    
    SET p_usuario_id = LAST_INSERT_ID();
END//

-- Leer
CREATE PROCEDURE sp_ObtenerUsuario(
    IN p_usuario_id INT
)
BEGIN
    SELECT * FROM Usuario WHERE usuario_id = p_usuario_id;
END//

-- Actualizar
CREATE PROCEDURE sp_ActualizarUsuario(
    IN p_usuario_id INT,
    IN p_Nombre VARCHAR(255),
    IN p_Contrasenia VARCHAR(255),
    IN p_rol ENUM('admin', 'jefe', 'empleado')
)
BEGIN
    UPDATE Usuario
    SET Nombre = p_Nombre,
        Contrasenia = p_Contrasenia,
        rol = p_rol
    WHERE usuario_id = p_usuario_id;
END//

-- Eliminar
CREATE PROCEDURE sp_EliminarUsuario(
    IN p_usuario_id INT
)
BEGIN
    DELETE FROM Usuario WHERE usuario_id = p_usuario_id;
END//

-- Autenticar
CREATE PROCEDURE sp_AutenticarUsuario(
    IN p_Nombre VARCHAR(255),
    IN p_Contrasenia VARCHAR(255),
    OUT p_is_authenticated BOOLEAN,
    OUT p_idUsuario INT
)
BEGIN
    DECLARE v_id INT;

    SELECT id INTO v_id
    FROM Usuario
    WHERE Nombre = p_Nombre 
    AND Contrasenia = p_Contrasenia;

    IF v_id IS NOT NULL THEN
        SET p_is_authenticated = TRUE;
        SET p_idUsuario = v_id;
    ELSE
        SET p_is_authenticated = FALSE;
        SET p_idUsuario = NULL;
    END IF;
END //

DELIMITER ;