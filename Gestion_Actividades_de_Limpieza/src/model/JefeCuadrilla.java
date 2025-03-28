package model;

public class JefeCuadrilla extends Usuario {

    // Jefe de cuadrilla 
    // tambien debe tener un atributo idCuadrilla para poder asociar a la cuadrilla que lidera

    private int idCuadrilla; // id de la cuadrilla que lidera

    public JefeCuadrilla(int idJefe, String nombreJefe, String passwordJefe, String rol, int idCuadrilla) {
        super(idJefe, nombreJefe, nombreJefe, "JefeCuadrilla");
        this.idCuadrilla = idCuadrilla; // id de la cuadrilla que lidera
    }

    // toString mantiene el formato de la clase padre Usuario solo que cambia el rol a "JefeCuadrilla"
    // agregar el idCuadrilla al toString para pruebas

    public int getIdCuadrilla() {
        return idCuadrilla;
    }

    public void setIdCuadrilla(int idCuadrilla) {
        this.idCuadrilla = idCuadrilla;
    }

    @Override   
    public String toString() {
        return "JefeCuadrilla [idJefe=" + getIdUsuario() + ", nombreJefe=" + getNombreUsuario() + 
                                ", passwordJefe=" + getPasswordUsuario() + ", rol=" + getRol() + "]";
    }

}
