package assets;

public enum ImagenRuta {

    ESCUDO_COAH("src/assets/images/escudo-de-coahuila-de-zaragoza.png"),

    ADD_ICON("src/assets/images/crud_icons/add_icon.png"),

    EDIT_ICON("src/assets/images/crud_icons/edit_icon.png"),

    SEARCH_ICON("src/assets/images/crud_icons/icons8-search-22.png"),

    READ_ICON("src/assets/images/crud_icons/read_icon.png"),

    REMOVE_ICON("src/assets/images/crud_icons/remove_icon.png"),
    
    FOOTER_IMAGE("src/assets/images/footer.png"),

    CLOSE_IMAGE("src/assets/images/close.png");

    private static final String RUTA_PROYECTO = "C:\\Users\\rodry\\.vscode\\Proyecto-arquitectura\\Gestion_Actividades_de_Limpieza\\";

    private final String subRuta;

    ImagenRuta(String subRuta) {
        this.subRuta = subRuta;
    }

    public String getRuta() {
        return RUTA_PROYECTO + subRuta;
    }

}
