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
import publicaciones.dto.ArticuloDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.ArticuloService;

@RestController
@RequestMapping("/articulo")
public class ArticuloController {
    
    @Autowired
    private ArticuloService service;

    @GetMapping
    public List<ResponseDto> listarArticulos() {
        return service.listarArticulos();
    }

    @PostMapping
    public ResponseDto crearArticulo(@RequestBody ArticuloDto articulo) {
        return service.crearArticulo(articulo);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarArticulo(@PathVariable Long id, @RequestBody ArticuloDto articulo) {
        return service.actualizarArticulo(id, articulo);
    }

    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id) {
        return service.buscarArticuloPorId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarArticulo(@PathVariable Long id) {
        return service.eliminarArticulo(id);
    }
}