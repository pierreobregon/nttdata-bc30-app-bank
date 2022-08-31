package pe.com.nttdata.service;

import pe.com.nttdata.model.Afiliado;
import reactor.core.publisher.Flux;

public interface IAfiliadoService {

    Flux<Afiliado> getAfiliado();
}
