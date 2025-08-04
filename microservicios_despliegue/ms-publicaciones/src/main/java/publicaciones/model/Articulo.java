package publicaciones.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Articulo extends Publicacion {

    @Column(nullable = false, length = 50)
    private String doi;
    private String revista;
    private String areaInvestigacion;
    private Date fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
