package dao.interfaces;

import java.sql.SQLException;
import java.util.List;

import model.Actividad;
public interface ActividadDAO {

    //CRUD de Actividad
    Actividad create(Actividad actividad) throws SQLException; //retornan el objeto creado
    Actividad read(int id) throws SQLException; //retorna el objeto encontrado
    Actividad update(Actividad actividad) throws SQLException; //retorna el objeto actualizado
    boolean delete(int id) throws SQLException; //retorna true si se elimino el objeto, false si no se encontro el objeto a eliminar

    //Consultas concretas
    List<Actividad> getAll() throws SQLException; //retorna una lista de objetos Actividad
    List<Actividad> getByColonia(int idColonia) throws SQLException; //retorna una lista de objetos Actividad por idColonia
    List<Actividad> getByCuadrilla(int idCuadrilla) throws SQLException; //retorna una lista de objetos Actividad por idCuadrilla
    List<Actividad> getByFecha(java.sql.Date fecha) throws SQLException; //retorna una lista de objetos Actividad por fecha
    
    //rutaImagen puede ser nullable? se puede dejar como vacío si se especifica que la actividad no tiene evidencia o sea que no ha sido terminada o sea que sigue en proceso...

}