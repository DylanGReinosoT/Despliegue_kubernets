package catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloDto {
    // Atributos del padre publicacion
    private String titulo;
    private String anioPublicacion;
    private String editorial;
    private String direccion;
    private String isbn;
    private String resumen;
    private String idioma;
    
    // Atributos específicos de articulo
    private String doi;
    private String revista;
    private String areaInvestigacion;
    private Date fechaPublicacion;
    
    // ID del autor (como llega desde publicaciones)
    private Long idAutor;
}