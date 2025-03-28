package model;

import java.util.Date;

public class Cuadrilla {

    // a través del idJefe de cuadrilla se puede acceder a la cuadrilla que lidera
    private int idJefeCuadrilla; // id del jefe de cuadrilla (fk)
    private int idCuadrilla;
    private String nombreCuadrilla;
    private Date fechaCreacionCuadrilla; // fecha de creación de la cuadrilla

    public Cuadrilla(int idJefeCuadrilla, int idCuadrilla, String nombreCuadrilla,
        Date fechaCreacionCuadrilla) {
        this.idJefeCuadrilla = idJefeCuadrilla;
        this.idCuadrilla = idCuadrilla;
        this.nombreCuadrilla = nombreCuadrilla;
        this.fechaCreacionCuadrilla = fechaCreacionCuadrilla;
    }

    public int getIdJefeCuadrilla() {
        return idJefeCuadrilla;
    }

    public void setIdJefeCuadrilla(int idJefeCuadrilla) {
        this.idJefeCuadrilla = idJefeCuadrilla;
    }

    public int getIdCuadrilla() {
        return idCuadrilla;
    }

    public void setIdCuadrilla(int idCuadrilla) {
        this.idCuadrilla = idCuadrilla;
    }

    public String getNombreCuadrilla() {
        return nombreCuadrilla;
    }

    public void setNombreCuadrilla(String nombreCuadrilla) {
        this.nombreCuadrilla = nombreCuadrilla;
    }

    public Date getFechaCreacionCuadrilla() {
        return fechaCreacionCuadrilla;
    }

    public void setFechaCreacionCuadrilla(Date fechaCreacionCuadrilla) {
        this.fechaCreacionCuadrilla = fechaCreacionCuadrilla;
    }

    @Override
    public String toString() {
        return "Cuadrilla [idJefeCuadrilla=" + idJefeCuadrilla + ", idCuadrilla=" + idCuadrilla
                + ", nombreCuadrilla=" + nombreCuadrilla + ", fechaCreacionCuadrilla="
                + fechaCreacionCuadrilla + "]";
    }

}
