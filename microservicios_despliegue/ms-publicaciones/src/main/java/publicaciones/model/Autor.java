package publicaciones.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Autores")
@Getter
@Setter

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, name = "nombres")
    private String nombre;

    @Column(nullable = false, length = 50, name = "apellidos")
    private String apellido;
    
    @Column(nullable = false, unique = true,length = 20)
    private String email;
    
    @Column(nullable = false, unique = true,length = 20)
    private String orcid;

    private String nacionalidad;
    private String telefono;
    private String institucion;

    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;

    @OneToMany(mappedBy = "autor")
    private List<Articulo> articulos;
}
