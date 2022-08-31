package com.bootcamp.passive.service;

import com.bootcamp.passive.controllers.AccountController;
import com.bootcamp.passive.models.documents.documents.Account;
import com.bootcamp.passive.models.documents.entities.ClientCompany;
import com.bootcamp.passive.models.documents.entities.Result;
import com.bootcamp.passive.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Date;

import static com.bootcamp.passive.models.documents.documents.Account.SEQUENCE_NAME;

@Service
@RequiredArgsConstructor
public class AccountService implements IAccountService{

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    
    @Override
    public Flux<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Mono<Account> findById(String id) {
        return accountRepository.findById(id).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Account> save(Account account) {
        account.setActive(true);
        return accountRepository.save(account);
    }

    @Override
    public Mono<Account> update(String id, Account account) {
        return accountRepository.findById(id).flatMap(account1 -> {
            account.setActive(true);
            account.setIdAccountNumber(id);
            account.setLastTransaction(new Date());
            account.setBalance(account.getBalance());
            account.setNumberTransactions(account.getNumberTransactions());
            return accountRepository.save(account);
        }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Account> delete(String id) {
        return accountRepository.findById(id).flatMap(account1 -> {
            account1.setActive(false);
            return accountRepository.save(account1);
        }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Result> RegisterAccountSaving(Account account, String type){
        Result result = new Result();
        Boolean isPerson;
        Boolean isCompany;

        // Nueva implementación
        String url = "http://localhost:18080/ClientCompany/{id}";
        Mono<ClientCompany> oClientCompanyMono = WebClient.create()
                .get()
                .uri(url,account.getCustomer())
                .retrieve()
                .bodyToMono(ClientCompany.class);
        
       return oClientCompanyMono
    		   .defaultIfEmpty(new ClientCompany() )
	        	.flatMap(p -> {
	        		System.out.println("ClientCompany Encontrado..."+ p);
			        		if(p.getId_client() == null) {
			        			return Mono.error(new InterruptedException("No existe el cliente"));
			        		}
			        		return Mono.just(p);
			        	})
        		.doOnNext(p -> log.info("ClientCompany Encontrado:"+p.getId_client() ) )
		    		.flatMap( e -> {
//				    			account.setIdAccountNumber(String.format("6565%010d", sequenceGeneratorService.getSequenceNumber(SEQUENCE_NAME)));
				    	        account.setRegisterDate(new Date());
				    	        account.setType(type);
				    	        account.setLastTransaction(new Date());
				    	        account.setNumberTransactions(0);
				    	        account.setActive(true);
				    			return accountRepository.save(account);	
				    		} )
		    			.flatMap(x ->{
		    	            result.setMessage("ACCOUNT CREATED!! new account number " + x.getIdAccountNumber());
		    	            return Mono.just(result);
		    	        });
        
//        return oClientCompanyMono.flatMap(x -> {
//            //afp.setId(x.getId());
//            //afp.setDescripcion(x.getDescripcion());
//            Mono<Result> resultTest = Mono.just(new Result("texto"));
//            return resultTest;
//        });
        
//        oClientCompanyMono.subscribe(client -> log.info(client.toString()));
//        return oClientCompanyMono;
//        
//        Mono<Afp> afpByAfiliado = this.webClient.get().uri(url"/ClientCompany/{id}", id).retrieve().bodyToMono(Afp.class);
//
//        return afpByAfiliado.flatMap(x -> {
//            afp.setId(x.getId());
//            afp.setDescripcion(x.getDescripcion());
//            Mono<Afiliado> afiliado = Mono.just(new Afiliado(1,"Juan", "44444444",1, afp));
//            return afiliado;
//        });
//        
//        
//        if (!clientService.FindClientCompanyId(account.getCustomer()).blockOptional().isPresent()
////        && !clientService.FindClientPersonId(account.getCustomer()).blockOptional().isPresent()
//        ){
//            result.setMessage("CANCELED -- CLient not exists!!");
//            return Mono.just(result);
//        }
//        account.setIdAccountNumber(String.format("6565%010d", sequenceGeneratorService.getSequenceNumber(SEQUENCE_NAME)));
//        account.setRegisterDate(new Date());
//        account.setType(type);
//        account.setLastTransaction(new Date());
//        account.setNumberTransactions(0);
//        account.setActive(true);
//        return accountRepository.save(account).flatMap(x -> {
//            result.setMessage("ACCOUNT CREATED!! new account number " + x.getIdAccountNumber());
//            return Mono.just(result);
//        });
    }
}
