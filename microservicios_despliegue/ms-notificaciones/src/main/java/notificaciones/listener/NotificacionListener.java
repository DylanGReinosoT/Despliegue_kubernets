package notificaciones.listener;

import notificaciones.dto.NotificacionDto;
import notificaciones.services.NotificacionService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NotificacionListener {

    @Autowired
    private NotificacionService service;

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "notificaciones.cola")
    public void recibirNotificacion(String mensajeJSON) {
        try {
            NotificacionDto dto = mapper.readValue(mensajeJSON, NotificacionDto.class);
            service.guardarNotificacion(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}