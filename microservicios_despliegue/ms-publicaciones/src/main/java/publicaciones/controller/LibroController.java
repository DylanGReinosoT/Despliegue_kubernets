package publicaciones.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import publicaciones.dto.LibroDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.LibroService;

@RestController
@RequestMapping("/libro")
public class LibroController {
    
    @Autowired
    private LibroService service;

    @GetMapping
    public List<ResponseDto> listarLibros() {
        return service.listarLibros();
    }

    @PostMapping
    public ResponseDto crearLibro(@RequestBody LibroDto libro) {
        return service.crearLibro(libro);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarLibro(@PathVariable Long id, @RequestBody LibroDto libro) {
        return service.actualizarLibro(id, libro);
    }

    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id) {
        return service.buscarLibroPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarLibro(@PathVariable Long id) {
        return service.eliminarLibro(id);
    }
}