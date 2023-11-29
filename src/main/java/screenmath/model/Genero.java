package screenmath.model;

public enum Genero {
    AÇÃO("Action", "Ação"),
    COMÉDIA("Comedy", "Comédia"),
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama","Drama"),
    FICÇÃO("Fiction","Ficção"),
    TERROR("Horror", "Horror"),
    CRIME("Crime", "Crime"),
    ANIMAÇÃO("Animation","Animação");

    private String categoriaOmbd;
    private String categoriaPortugues;

    Genero(String categoriaOmbd, String categoriaPortugues){
        this.categoriaOmbd = categoriaOmbd;
        this.categoriaPortugues = categoriaPortugues;
    }
    public static Genero fromString(String text) {
        for (Genero genero : Genero.values()) {
            if (genero.categoriaOmbd.equalsIgnoreCase(text)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Genero fromPortugues(String text) {
        for (Genero genero : Genero.values()) {
            System.out.println(genero);
                if (genero.categoriaPortugues.equalsIgnoreCase(text)) {
                        return genero;
                }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
}
}
