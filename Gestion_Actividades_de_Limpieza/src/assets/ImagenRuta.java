package assets;

public enum ImagenRuta {

    ESCUDO_COAH("src/assets/images/escudo-de-coahuila-de-zaragoza.png"),

    ADD_ICON("src/assets/images/add_icon.png"),

    EDIT_ICON("src/assets/images/edit_icon.png"),

    SEARCH_ICON("src/assets/images/icons8-search-22.png"),

    READ_ICON("src/assets/images/read_icon.png"),

    REMOVE_ICON("src/assets/images/remove_icon.png"),
    
    FOOTER_IMAGE("src/assets/images/footer.png");

    private static final String RUTA_PROYECTO = "C:\\Users\\rodry\\.vscode\\Proyecto-arquitectura\\Gestion_Actividades_de_Limpieza\\";

    private final String subRuta;

    ImagenRuta(String subRuta) {
        this.subRuta = subRuta;
    }

    public String getRuta() {
        return RUTA_PROYECTO + subRuta;
    }

}
