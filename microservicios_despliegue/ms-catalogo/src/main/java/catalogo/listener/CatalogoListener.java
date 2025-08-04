package catalogo.listener;

import catalogo.dto.CatalogoDto;
import catalogo.service.CatalogoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogoListener {

    @Autowired
    private CatalogoService service;

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "catalogo.cola")
    public void recibirMensaje(String mensajeJSON) {
        try {
            System.out.println("Mensaje recibido en catálogo: " + mensajeJSON);
            
            CatalogoDto dto = mapper.readValue(mensajeJSON, CatalogoDto.class);
            service.procesarMensaje(dto);
            
            System.out.println("Mensaje procesado correctamente en catálogo");
            
        } catch (Exception e) {
            System.err.println("Error procesando mensaje en catálogo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}