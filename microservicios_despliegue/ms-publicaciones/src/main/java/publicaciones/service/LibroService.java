package publicaciones.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.LibroDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.model.Libro;
import publicaciones.repository.AutorRepository;
import publicaciones.repository.LibroRepository;

@Service
public class LibroService {
    
    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;

    public ResponseDto crearLibro(LibroDto dto) {
        Autor autor = autorRepository.findById(dto.getIdAutor())
            .orElseThrow(() -> new RuntimeException("Autor con id " + dto.getIdAutor() + " no encontrado"));
        
        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setEditorial(dto.getEditorial());
        libro.setDireccion(dto.getDireccion());
        libro.setIsbn(dto.getIsbn());
        libro.setResumen(dto.getResumen());
        libro.setIdioma(dto.getIdioma());
        libro.setGenero(dto.getGenero());
        libro.setNumeroPaginas(dto.getNumeroPaginas());
        libro.setEdicion(dto.getEdicion());
        libro.setAutor(autor);

        Libro saved = libroRepository.save(libro);
        LibroDto libroParaCatalogo = crearLibroDtoParaCatalogo(saved);
        producer.enviarACatalogo("Libro creado: " + saved.getTitulo(), "libro_creado", libroParaCatalogo);
        
        return new ResponseDto("Libro creado correctamente", saved);
    }
    
    private LibroDto crearLibroDtoParaCatalogo(Libro libro) {
        LibroDto dto = new LibroDto();
        dto.setTitulo(libro.getTitulo());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setEditorial(libro.getEditorial());
        dto.setDireccion(libro.getDireccion());
        dto.setIsbn(libro.getIsbn());
        dto.setResumen(libro.getResumen());
        dto.setIdioma(libro.getIdioma());
        dto.setGenero(libro.getGenero());
        dto.setNumeroPaginas(libro.getNumeroPaginas());
        dto.setEdicion(libro.getEdicion());
        dto.setIdAutor(libro.getAutor().getId());
        return dto;
    }

    public ResponseDto actualizarLibro(Long id, LibroDto dto) {
        Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Libro con id " + id + " no encontrado"));
        
        Autor autor = autorRepository.findById(dto.getIdAutor())
            .orElseThrow(() -> new RuntimeException("Autor con id " + dto.getIdAutor() + " no encontrado"));
        
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setEditorial(dto.getEditorial());
        libro.setDireccion(dto.getDireccion());
        libro.setIsbn(dto.getIsbn());
        libro.setResumen(dto.getResumen());
        libro.setIdioma(libro.getIdioma());
        libro.setGenero(dto.getGenero());
        libro.setNumeroPaginas(dto.getNumeroPaginas());
        libro.setEdicion(dto.getEdicion());
        libro.setAutor(autor);
        
        return new ResponseDto(
            "Libro actualizado correctamente", 
            libroRepository.save(libro));
    }

    public List<ResponseDto> listarLibros() {
        return libroRepository.findAll()
            .stream()
            .map(l -> new ResponseDto("Libro: " + l.getTitulo(), l))
            .collect(Collectors.toList());
    }

    public ResponseDto eliminarLibro(Long id) {
        Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Libro con id " + id + " no encontrado"));
        libroRepository.delete(libro);
        return new ResponseDto("Libro eliminado correctamente", null);
    }

    public ResponseDto buscarLibroPorId(Long id) {
        Libro libro = libroRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Libro con id " + id + " no encontrado"));
        return new ResponseDto("Libro: " + libro.getTitulo(), libro);
    }
}