package model;

public class Colonia {

    private int cveColonia;
    private String nombreColonia;

    public Colonia(int cveColonia, String nombreColonia) {
        this.cveColonia = cveColonia;
        this.nombreColonia = nombreColonia;
    }

    public int getCveColonia() {
        return cveColonia;
    }

    public void setCveColonia(int cveColonia) {
        this.cveColonia = cveColonia;
    }

    public String getNombreColonia() {
        return nombreColonia;
    }

    public void setNombreColonia(String nombreColonia) {
        this.nombreColonia = nombreColonia;
    }

    @Override
    public String toString() {
        return "Colonia [cveColonia=" + cveColonia + ", nombreColonia=" + nombreColonia + "]";
    }

}
