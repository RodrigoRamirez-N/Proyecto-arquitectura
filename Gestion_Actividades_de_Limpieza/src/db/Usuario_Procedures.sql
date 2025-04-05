-- CRUD Completo para Usuario
USE GestionLimpieza;
DELIMITER //

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
    IN p_Contrasenia VARCHAR(255)
)
BEGIN
    SELECT usuario_id, Nombre, rol 
    FROM Usuario 
    WHERE Nombre = p_Nombre 
    AND Contrasenia = p_Contrasenia;
END//

DELIMITER ;