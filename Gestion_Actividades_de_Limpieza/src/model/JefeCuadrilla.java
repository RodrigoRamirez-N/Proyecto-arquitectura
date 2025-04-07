package model;

public class JefeCuadrilla extends Usuario {

    // Jefe de cuadrilla 
    // tambien debe tener un atributo telefono para poder asociar a la cuadrilla que lidera

    private String telefono; // id de la cuadrilla que lidera

    public JefeCuadrilla(int idJefe, String nombreJefe, String passwordJefe, String rol, String telefono) {
        super(idJefe, nombreJefe, nombreJefe, "jefe");
        this.telefono = telefono; // id de la cuadrilla que lidera
    }

    // toString mantiene el formato de la clase padre Usuario solo que cambia el rol a "JefeCuadrilla"
    // agregar el telefono al toString para pruebas

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override   
    public String toString() {
        return "JefeCuadrilla [idJefe=" + getId() + ", nombreJefe=" + getNombre() + 
                                ", passwordJefe=" + getPassword() + ", rol=" + getRol() + "]";
    }

}
