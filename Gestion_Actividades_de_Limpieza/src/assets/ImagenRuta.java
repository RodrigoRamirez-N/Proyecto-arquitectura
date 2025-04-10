package assets;

public enum ImagenRuta {

    ESCUDO_COAH("src/assets/images/escudo-de-coahuila-de-zaragoza.png");

    private static final String RUTA_PROYECTO = "C:\\Users\\rodry\\.vscode\\Proyecto-arquitectura\\Gestion_Actividades_de_Limpieza\\";

    private final String subRuta;

    ImagenRuta(String subRuta) {
        this.subRuta = subRuta;
    }

    public String getRuta() {
        return RUTA_PROYECTO + subRuta;
    }

}
