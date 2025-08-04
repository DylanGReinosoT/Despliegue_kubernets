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

import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.AutorService;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService service;

    @GetMapping
    public List<ResponseDto> listarAutores() {
        return service.listarAutores();
    }

    @PostMapping
    public ResponseDto crearAutor(@RequestBody AutorDto autor) {
        return service.crearAutor(autor);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarAutor(@PathVariable Long id, @RequestBody AutorDto autor) {
        return service.actualizarAutor(id, autor);
    }

    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id) {
        return service.buscarAutorPorId(id);
    }

    //eliminar
    @DeleteMapping("/{id}")
    public ResponseDto eliminarAutor(@PathVariable Long id) {
        return service.eliminarAutor(id);
    }
}
