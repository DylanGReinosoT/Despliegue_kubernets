package publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDto {
    //Atributos del padre publicacion
    private String titulo;
    private String anioPublicacion;
    private String editorial;
    private String direccion;
    private String isbn;
    private String resumen;
    private String idioma;

    //Atributos de libro
    private String genero;
    private String numeroPaginas;
    private String edicion;

    private Long idAutor;

}
