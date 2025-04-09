package model;

public class Empleado extends Usuario {
    private String telefono;

    public Empleado(int idEmpleado, String nombreEmpleado, String passwordEmpleado, String rol, String telefono) {
        super(idEmpleado, nombreEmpleado, passwordEmpleado, rol);
        this.telefono = telefono; 
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return "Empleado [idEmpleado=" + getId() + ", nombreEmpleado=" + getNombre() + 
                                ", passwordEmpleado=" + getPassword() + ", rol=" + getRol() + "]";
    }
    
}
