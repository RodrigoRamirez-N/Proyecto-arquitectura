-
DROP TABLE IF EXISTS Actividad;
DROP TABLE IF EXISTS Cuadrilla;
DROP TABLE IF EXISTS Jefe_Cuadrilla;
DROP TABLE IF EXISTS Usuario;
DROP TABLE IF EXISTS Colonia;

CREATE TABLE Usuario (
    usuario_id INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    Contrasenia VARCHAR(255) NOT NULL, 
    rol VARCHAR(255) NOT NULL 
);


CREATE TABLE Jefe_Cuadrilla (
    jefe_cuadrilla_id INT PRIMARY KEY AUTO_INCREMENT,
    Nombre VARCHAR(255) NOT NULL,
    usuario_id INT NOT NULL, 
    FOREIGN KEY (usuario_id) REFERENCES Usuario(usuario_id)
) ;


CREATE TABLE Colonia (
    cve_colonia INT PRIMARY KEY, 
    NombreColonia VARCHAR(255) NULL 
) ;

CREATE TABLE Cuadrilla (
    cuadrilla_id INT PRIMARY KEY AUTO_INCREMENT,
    NombreCuadrilla VARCHAR(255) NOT NULL,
    jefe_id INT NOT NULL,
    Fecha DATE NULL, 
    FOREIGN KEY (jefe_id) REFERENCES Jefe_Cuadrilla(jefe_cuadrilla_id)
        ON DELETE RESTRICT ON UPDATE CASCADE
) ;

CREATE TABLE Actividad (
    actividad_id INT PRIMARY KEY AUTO_INCREMENT,
    Descripcion VARCHAR(1000) NULL,
    Fecha DATE NOT NULL,
    imagenEvidencia LONGTEXT NULL, 
    cuadrilla_id INT NULL, 
    cve_colonia INT NOT NULL, 
    usuario_registro_id INT NOT NULL, 
    FOREIGN KEY (cuadrilla_id) REFERENCES Cuadrilla(cuadrilla_id)
    FOREIGN KEY (cve_colonia) REFERENCES Colonia(cve_colonia)
    FOREIGN KEY (usuario_registro_id) REFERENCES Usuario(usuario_id)
) ;
