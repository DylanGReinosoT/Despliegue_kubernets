package publicaciones.service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.repository.AutorRepository;

@Service
public class AutorService {
    
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;

    public ResponseDto crearAutor(AutorDto dato) {
        Autor autor = new Autor();
        autor.setNombre(dato.getNombre());
        autor.setApellido(dato.getApellido());
        autor.setEmail(dato.getEmail());
        autor.setOrcid(dato.getOrcid());
        autor.setInstitucion(dato.getInstitucion());
        autor.setNacionalidad(dato.getNacionalidad());
        autor.setTelefono(dato.getTelefono());

        Autor saved = autorRepository.save(autor);
        producer.enviarNotificacion("Autor creado: " + saved.getNombre() + " " + saved.getApellido(), "autor_creado");
        return new ResponseDto("Autor creado correctamente", saved);
    }

    public ResponseDto actualizarAutor(Long id, AutorDto dto){
        Autor autor = autorRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Autor con id " + id + " no encontrado"));

        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setEmail(dto.getEmail());
        autor.setOrcid(dto.getOrcid());
        autor.setInstitucion(dto.getInstitucion());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setTelefono(dto.getTelefono());
        return new ResponseDto(
            "Autor actualizado correctamente", 
            autorRepository.save(autor));

    } 

    public List<ResponseDto> listarAutores() {
        return autorRepository.findAll()
            .stream()
            .map(a -> new ResponseDto("Autor: " + a.getNombre(), a))
            .collect(Collectors.toList());          
    }

    public ResponseDto eliminarAutor(Long id) {
        Autor autor = autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor con id " + id + " no encontrado"));
        autorRepository.delete(autor);
        return new ResponseDto("Autor eliminado correctamente", null);
    }

    public ResponseDto buscarAutorPorId(Long id) {
        Autor autor = autorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Autor con id " + id + " no encontrado"));
        return new ResponseDto("Autor: " + autor.getNombre(), autor);
    } 
}