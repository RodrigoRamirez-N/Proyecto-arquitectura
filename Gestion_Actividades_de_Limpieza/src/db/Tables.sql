USE GestionLimpieza;

-- Nueva Tabla Empleado (especialización de Usuario)
-- CREATE TABLE Empleado (
--     empleado_id INT PRIMARY KEY,
--     telefono VARCHAR(10) NULL,
--     FOREIGN KEY (empleado_id) REFERENCES Usuario(usuario_id)
--         ON DELETE CASCADE
-- );


DROP TABLE IF EXISTS Actividad;
DROP TABLE IF EXISTS Empleado_Cuadrilla;
DROP TABLE IF EXISTS Empleado;
DROP TABLE IF EXISTS Cuadrilla;
DROP TABLE IF EXISTS Jefe_Cuadrilla;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Colonia;



-- select rol from a combo box when creating a new user
CREATE TABLE Usuario (
    usuario_id INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Contrasenia VARCHAR(255) NOT NULL, 
    rol ENUM('admin', 'jefe', 'empleado') NOT NULL
);

-- Nueva estructura con herencia
-- no es necesaria la columna nombre porque se hereda de la tabla Usuario
CREATE TABLE Jefe_Cuadrilla (
    jefe_cuadrilla_id INT PRIMARY KEY,
    telefono VARCHAR(10) NULL,
    FOREIGN KEY (jefe_cuadrilla_id) REFERENCES Usuario(usuario_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Nueva tabla Empleado (especialización de Usuario)
CREATE TABLE Empleado (
    empleado_id INT PRIMARY KEY,
    telefono VARCHAR(10) NULL,
    FOREIGN KEY (empleado_id) REFERENCES Usuario(usuario_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE Colonia (
    cve_colonia INT PRIMARY KEY AUTO_INCREMENT, 
    NombreColonia VARCHAR(255) NOT NULL 
) ;

CREATE TABLE Cuadrilla (
    cuadrilla_id INT PRIMARY KEY AUTO_INCREMENT,
    NombreCuadrilla VARCHAR(255) NOT NULL,
    jefe_id INT NULL,
    Fecha DATE NULL, 
    FOREIGN KEY (jefe_id) REFERENCES Jefe_Cuadrilla(jefe_cuadrilla_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ;

-- Tabla intermedia
CREATE TABLE Empleado_Cuadrilla (
    cuadrilla_id INT,
    empleado_id INT,
    PRIMARY KEY (cuadrilla_id, empleado_id),
    FOREIGN KEY (cuadrilla_id) REFERENCES Cuadrilla(cuadrilla_id)
        ON DELETE CASCADE,
    FOREIGN KEY (empleado_id) REFERENCES Empleado(empleado_id)
        ON DELETE CASCADE
);

-- select estado from a combo box 
-- select imagenEvidencia from a file chooser
-- select fecha from date picker
CREATE TABLE Actividad (
    actividad_id INT PRIMARY KEY AUTO_INCREMENT,
    detalles LONGTEXT NULL,
    tipoActividad ENUM('Barrido manual', 'Limpieza parques', 'Deshierbe de banquetas', 'Limpieza de calles', 'Recoleccion de basura', 'Limpieza de alcantarillas', 'Limpieza de basureros publicos', 'Limpieza general') DEFAULT 'Limpieza general',
    Fecha DATE NOT NULL,
    imagenEvidencia LONGTEXT NULL, 
    estado ENUM('Pendiente', 'En Proceso', 'Finalizada') DEFAULT 'Pendiente',
    cuadrilla_id INT NULL, 
    cve_colonia INT NOT NULL, 
    usuario_registro_id INT NOT NULL, 
    FOREIGN KEY (cuadrilla_id) REFERENCES Cuadrilla(cuadrilla_id)
        ON DELETE SET NULL,
    FOREIGN KEY (cve_colonia) REFERENCES Colonia(cve_colonia)
        ON DELETE RESTRICT,
    FOREIGN KEY (usuario_registro_id) REFERENCES Usuario(usuario_id)
        ON DELETE RESTRICT
) ;
