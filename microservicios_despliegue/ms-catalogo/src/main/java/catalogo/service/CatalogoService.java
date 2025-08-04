package catalogo.service;

import catalogo.dto.ArticuloDto;
import catalogo.dto.CatalogoDto;
import catalogo.dto.LibroDto;
import catalogo.entity.Catalogo;
import catalogo.repository.CatalogoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private CatalogoRepository repository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NotificacionProducer notificacionProducer;

    public void procesarMensaje(CatalogoDto dto) {
        try {
            Catalogo catalogo = new Catalogo();
            catalogo.setFechaRegistro(LocalDateTime.now().toString());

            // Detectar el tipo basado en el mensaje o los datos
            if (dto.getTipo().contains("libro") || esLibro(dto.getDatos())) {
                procesarLibro(catalogo, dto);
            } else if (dto.getTipo().contains("articulo") || esArticulo(dto.getDatos())) {
                procesarArticulo(catalogo, dto);
            } else {
                // Procesamiento genérico
                catalogo.setTipo("desconocido");
                catalogo.setTitulo("Publicación recibida");
            }

            repository.save(catalogo);
            
            // Después de guardar en catálogo, enviar notificación
            notificacionProducer.enviarNotificacion(
                "Catalogado: " + dto.getMensaje(), 
                "catalogo_" + dto.getTipo()
            );

        } catch (Exception e) {
            System.err.println("Error procesando mensaje en catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void procesarLibro(Catalogo catalogo, CatalogoDto dto) {
        try {
            Object datos = dto.getDatos();
            LibroDto libro;

            if (datos instanceof LibroDto) {
                libro = (LibroDto) datos;
            } else {
                String json = mapper.writeValueAsString(datos);
                libro = mapper.readValue(json, LibroDto.class);
            }

            catalogo.setTipo("libro");
            catalogo.setTitulo(libro.getTitulo());
            catalogo.setIdAutor(libro.getIdAutor());
            catalogo.setAnioPublicacion(libro.getAnioPublicacion());
            catalogo.setEditorial(libro.getEditorial());
            catalogo.setIsbn(libro.getIsbn());
            catalogo.setResumen(libro.getResumen());
            catalogo.setIdioma(libro.getIdioma());
            catalogo.setDireccion(libro.getDireccion());

            // Campos específicos de libro
            catalogo.setGenero(libro.getGenero());
            catalogo.setNumeroPaginas(libro.getNumeroPaginas());
            catalogo.setEdicion(libro.getEdicion());

        } catch (Exception e) {
            catalogo.setTipo("libro");
            catalogo.setTitulo("Libro registrado");
            catalogo.setIdAutor(null);
            e.printStackTrace();
        }
    }

    private void procesarArticulo(Catalogo catalogo, CatalogoDto dto) {
        try {
            Object datos = dto.getDatos();
            ArticuloDto articulo;

            if (datos instanceof ArticuloDto) {
                articulo = (ArticuloDto) datos;
            } else {
                String json = mapper.writeValueAsString(datos);
                articulo = mapper.readValue(json, ArticuloDto.class);
            }

            catalogo.setTipo("articulo");
            catalogo.setTitulo(articulo.getTitulo());
            catalogo.setIdAutor(articulo.getIdAutor());
            catalogo.setAnioPublicacion(articulo.getAnioPublicacion());
            catalogo.setEditorial(articulo.getEditorial());
            catalogo.setIsbn(articulo.getIsbn());
            catalogo.setResumen(articulo.getResumen());
            catalogo.setIdioma(articulo.getIdioma());
            catalogo.setDireccion(articulo.getDireccion());

            // Campos específicos de artículo
            catalogo.setDoi(articulo.getDoi());
            catalogo.setRevista(articulo.getRevista());
            catalogo.setAreaInvestigacion(articulo.getAreaInvestigacion());
            catalogo.setFechaPublicacion(
                    articulo.getFechaPublicacion() != null ? articulo.getFechaPublicacion().toString() : null);

        } catch (Exception e) {
            catalogo.setTipo("articulo");
            catalogo.setTitulo("Artículo registrado");
            catalogo.setIdAutor(null);
            e.printStackTrace();
        }
    }

    private boolean esLibro(Object datos) {
        try {
            String json = mapper.writeValueAsString(datos);
            return json.contains("genero") || json.contains("numeroPaginas") || json.contains("edicion");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean esArticulo(Object datos) {
        try {
            String json = mapper.writeValueAsString(datos);
            return json.contains("doi") || json.contains("revista") || json.contains("areaInvestigacion");
        } catch (Exception e) {
            return false;
        }
    }

    public List<Catalogo> obtenerTodos() {
        return repository.findAll();
    }
}