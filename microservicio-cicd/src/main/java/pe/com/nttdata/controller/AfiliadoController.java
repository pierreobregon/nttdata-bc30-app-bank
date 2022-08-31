package pe.com.nttdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.com.nttdata.model.Afiliado;
import pe.com.nttdata.service.IAfiliadoService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/afiliado")
public class AfiliadoController {

    @Autowired
    IAfiliadoService service;

    @GetMapping
    public Flux<Afiliado> listarAfiliados(){
        return service.getAfiliado();
    }
}
