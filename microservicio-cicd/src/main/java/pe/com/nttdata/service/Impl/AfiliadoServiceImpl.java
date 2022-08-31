package pe.com.nttdata.service.Impl;

import org.springframework.stereotype.Service;
import pe.com.nttdata.model.Afiliado;
import pe.com.nttdata.service.IAfiliadoService;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class AfiliadoServiceImpl implements IAfiliadoService {

    @Override
    public Flux<Afiliado> getAfiliado() {

        //Mono<Afiliado> afiliado1 = Mono.just(new Afiliado(1,"Juan", "44444444"));
        //Mono<Afiliado> afiliado2 = Mono.just(new Afiliado(1,"Juan", "44444444"));
        //Mono<Afiliado> afiliado3 = Mono.just(new Afiliado(1,"Juan", "44444444"));
        List<Afiliado> listaAfiliados = new ArrayList<>();
        listaAfiliados.add(new Afiliado(1,"Juan", "44444444"));
        listaAfiliados.add(new Afiliado(2,"Juan", "44444444"));
        listaAfiliados.add(new Afiliado(3,"Juan", "44444444"));
        Flux<Afiliado> afiliados = Flux.fromIterable(listaAfiliados);
        return afiliados;
    }

}
