package model;

import java.util.Date;

public class Actividad {

    private int actividad_id; 
    private String descripcion; 
    private Date fecha;
    private String imagenEvidencia; 
    private String estado;
    private int cuadrilla_id; 
    private int cve_colonia; 
    private int usuario_registro_id; 


    public Actividad(int actividad_id, String descripcion, Date fecha, String imagenEvidencia, 
            String estado, int cuadrilla_id, int cve_colonia, int usuario_registro_id) {
        this.actividad_id = actividad_id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagenEvidencia = imagenEvidencia;
        this.estado = estado;
        this.cuadrilla_id = cuadrilla_id;
        this.cve_colonia = cve_colonia;
        this.usuario_registro_id = usuario_registro_id;
    }

    public int getActividad_id() {
        return actividad_id;
    }

    public void setActividad_id(int actividad_id) {
        this.actividad_id = actividad_id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getImagenEvidencia() {
        return imagenEvidencia;
    }

    public void setImagenEvidencia(String imagenEvidencia) {
        this.imagenEvidencia = imagenEvidencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCuadrilla_id() {
        return cuadrilla_id;
    }

    public void setCuadrilla_id(int cuadrilla_id) {
        this.cuadrilla_id = cuadrilla_id;
    }

    public int getCve_colonia() {
        return cve_colonia;
    }

    public void setCve_colonia(int cve_colonia) {
        this.cve_colonia = cve_colonia;
    }

    public int getUsuario_registro_id() {
        return usuario_registro_id;
    }

    public void setUsuario_registro_id(int usuario_registro_id) {
        this.usuario_registro_id = usuario_registro_id;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "actividad_id=" + actividad_id +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", imagenEvidencia='" + imagenEvidencia + '\'' +
                ", estado='" + estado + '\'' +
                ", cuadrilla_id=" + cuadrilla_id +
                ", cve_colonia=" + cve_colonia +
                ", usuario_registro_id=" + usuario_registro_id +
                '}';
    }

}
