package publicaciones.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.ArticuloDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Articulo;
import publicaciones.model.Autor;
import publicaciones.repository.ArticuloRepository;
import publicaciones.repository.AutorRepository;

@Service
public class ArticuloService {
    
    @Autowired
    private ArticuloRepository articuloRepository;
    
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;

    public ResponseDto crearArticulo(ArticuloDto dto) {
        Autor autor = autorRepository.findById(dto.getIdAutor())
            .orElseThrow(() -> new RuntimeException("Autor con id " + dto.getIdAutor() + " no encontrado"));
        
        Articulo articulo = new Articulo();
        articulo.setTitulo(dto.getTitulo());
        articulo.setAnioPublicacion(dto.getAnioPublicacion());
        articulo.setEditorial(dto.getEditorial());
        articulo.setDireccion(dto.getDireccion());
        articulo.setIsbn(dto.getIsbn());
        articulo.setResumen(dto.getResumen());
        articulo.setIdioma(dto.getIdioma());
        articulo.setDoi(dto.getDoi());
        articulo.setRevista(dto.getRevista());
        articulo.setAreaInvestigacion(dto.getAreaInvestigacion());
        articulo.setFechaPublicacion(dto.getFechaPublicacion());
        articulo.setAutor(autor);
        
        Articulo saved = articuloRepository.save(articulo);
        ArticuloDto articuloParaCatalogo = crearArticuloDtoParaCatalogo(saved);
        producer.enviarACatalogo("Artículo creado: " + saved.getTitulo(), "articulo_creado", articuloParaCatalogo);

        return new ResponseDto("Artículo creado correctamente", saved);
    }

    private ArticuloDto crearArticuloDtoParaCatalogo(Articulo articulo) {
        ArticuloDto dto = new ArticuloDto();
        dto.setTitulo(articulo.getTitulo());
        dto.setAnioPublicacion(articulo.getAnioPublicacion());
        dto.setEditorial(articulo.getEditorial());
        dto.setDireccion(articulo.getDireccion());
        dto.setIsbn(articulo.getIsbn());
        dto.setResumen(articulo.getResumen());
        dto.setIdioma(articulo.getIdioma());
        dto.setDoi(articulo.getDoi());
        dto.setRevista(articulo.getRevista());
        dto.setAreaInvestigacion(articulo.getAreaInvestigacion());
        dto.setFechaPublicacion(articulo.getFechaPublicacion());
        dto.setIdAutor(articulo.getAutor().getId());
        return dto;
    }
    
    public ResponseDto actualizarArticulo(Long id, ArticuloDto dto) {
        Articulo articulo = articuloRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Artículo con id " + id + " no encontrado"));
        
        Autor autor = autorRepository.findById(dto.getIdAutor())
            .orElseThrow(() -> new RuntimeException("Autor con id " + dto.getIdAutor() + " no encontrado"));
        
        articulo.setTitulo(dto.getTitulo());
        articulo.setAnioPublicacion(dto.getAnioPublicacion());
        articulo.setEditorial(dto.getEditorial());
        articulo.setDireccion(dto.getDireccion());
        articulo.setIsbn(dto.getIsbn());
        articulo.setResumen(dto.getResumen());
        articulo.setIdioma(dto.getIdioma());
        articulo.setDoi(dto.getDoi());
        articulo.setRevista(dto.getRevista());
        articulo.setAreaInvestigacion(dto.getAreaInvestigacion());
        articulo.setFechaPublicacion(dto.getFechaPublicacion());
        articulo.setAutor(autor);
        
        return new ResponseDto(
            "Artículo actualizado correctamente", 
            articuloRepository.save(articulo));
    }

    public List<ResponseDto> listarArticulos() {
        return articuloRepository.findAll()
            .stream()
            .map(a -> new ResponseDto("Artículo: " + a.getTitulo(), a))
            .collect(Collectors.toList());
    }

    public ResponseDto eliminarArticulo(Long id) {
        Articulo articulo = articuloRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Artículo con id " + id + " no encontrado"));
        articuloRepository.delete(articulo);
        return new ResponseDto("Artículo eliminado correctamente", null);
    }

    public ResponseDto buscarArticuloPorId(Long id) {
        Articulo articulo = articuloRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Artículo con id " + id + " no encontrado"));
        return new ResponseDto("Artículo: " + articulo.getTitulo(), articulo);
    }
}