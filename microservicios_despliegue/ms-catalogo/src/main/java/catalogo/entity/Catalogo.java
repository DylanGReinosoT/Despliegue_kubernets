package catalogo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String tipo; // "libro" o "articulo"
    private Long idAutor;

    private String anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
    
    // Campos específicos para libros
    private String genero;
    private String numeroPaginas;
    private String edicion;
    
    // Campos específicos para artículos
    private String doi;
    private String revista;
    private String areaInvestigacion;
    private String fechaPublicacion;
    
    private String fechaRegistro;
    private String direccion;
}