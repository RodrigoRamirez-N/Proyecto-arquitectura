package model;

public class Empleado extends Usuario {
    private String telefono; // id de la cuadrilla que lidera

    public Empleado(int idEmpleado, String nombreEmpleado, String passwordEmpleado, String rol, String telefono) {
        super(idEmpleado, nombreEmpleado, passwordEmpleado, rol);
        this.telefono = telefono; // id de la cuadrilla que lidera
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return "Empleado [idEmpleado=" + getIdUsuario() + ", nombreEmpleado=" + getNombreUsuario() + 
                                ", passwordEmpleado=" + getPasswordUsuario() + ", rol=" + getRol() + "]";
    }
    
}
