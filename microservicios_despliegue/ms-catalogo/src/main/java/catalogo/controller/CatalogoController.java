package catalogo.controller;

import catalogo.entity.Catalogo;
import catalogo.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @GetMapping
    public List<Catalogo> obtenerTodos() {
        return catalogoService.obtenerTodos();
    }
}