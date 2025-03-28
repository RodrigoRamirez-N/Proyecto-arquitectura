package model;

import java.util.Date;

public class Actividad {

    private int idActividad;
    private String descripcionActivdad;
    private Date fechaActividad;
    private String evidenciaURL;
    private int idCuadrilla; // fk cuadrilla
    private int idColonia; // fk colonia
    private int idUsuario; // fk usuario (jefe de cuadrilla)

    public Actividad(int idActividad, String descripcionActivdad, Date fechaActividad, String evidenciaURL,
            int idCuadrilla, int idColonia, int idUsuario) {
        this.idActividad = idActividad;
        this.descripcionActivdad = descripcionActivdad;
        this.fechaActividad = fechaActividad;
        this.evidenciaURL = evidenciaURL;
        this.idCuadrilla = idCuadrilla;
        this.idColonia = idColonia;
        this.idUsuario = idUsuario;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getDescripcionActivdad() {
        return descripcionActivdad;
    }

    public void setDescripcionActivdad(String descripcionActivdad) {
        this.descripcionActivdad = descripcionActivdad;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public String getEvidenciaURL() {
        return evidenciaURL;
    }

    public void setEvidenciaURL(String evidenciaURL) {
        this.evidenciaURL = evidenciaURL;
    }

    public int getIdCuadrilla() {
        return idCuadrilla;
    }

    public void setIdCuadrilla(int idCuadrilla) {
        this.idCuadrilla = idCuadrilla;
    }

    public int getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(int idColonia) {
        this.idColonia = idColonia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    @Override
    public String toString() {
        return "Actividad [idActividad=" + idActividad + ", descripcionActivdad=" + descripcionActivdad
                + ", fechaActividad=" + fechaActividad + ", evidenciaURL=" + evidenciaURL + ", idCuadrilla="
                + idCuadrilla + ", idColonia=" + idColonia + ", idUsuario=" + idUsuario + "]";
    }

}
