package io.github.dougllasfps.rest.controller;

import io.github.dougllasfps.domain.entity.Cliente;
import io.github.dougllasfps.domain.repository.Clientes;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController//com essa annotation todos os métodos já ficam com response body.
@RequestMapping("/api/clientes")
public class ClienteController {

    private Clientes clientes;

    public ClienteController(Clientes clientes){
        this.clientes = clientes;
    }

    @GetMapping("/{id}")
    //equivalente ao requestMapping com method get
    //@ResponseBody//o return vai ser o corpo da resposta, o response body faz isso
    public Cliente getClienteById( @PathVariable Integer id){
        return clientes
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @PostMapping
    //@ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save( @RequestBody @Valid Cliente cliente){
        return clientes.save(cliente);

    }

    //@ResponseBody
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        clientes.findById(id)
                .map( cliente -> {
                    clientes.delete(cliente );
                    return cliente;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado") );
    }

    //so lembrando q request body é da requisicao, no body da requisicao, e o res-
    //ponse body é o retorno, a resposta ao servidor
    @PutMapping("/{id}")
    //@ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
         clientes
                .findById(id)
                .map( clienteExistente -> {
                    cliente.setId(clienteExistente.getId());
                    clientes.save(cliente);
                    return clienteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                         "Cliente não encontrado") );
    }

    @GetMapping
    public List<Cliente> find(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(
                                            ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return clientes.findAll(example);

    }


    /*@RequestMapping(
            value="/hello/{nome}",
            //da pra colocar um array de string no value tipo {"hello/{nome}", "/api/hello"}
            method= RequestMethod.GET,
            consumes = { "application/json", "application/xml"},//esses tipos não fazem sentido aqui pois não vai receber por exemplo
            produces = {"application/json", "application/xml" }
            //um objeto no body em nenhum desses formatos. Então num post faria sentido, aqui no get não.
    )
    @ResponseBody//o return vai ser o corpo da resposta, o response body faz isso
    public String helloCliente( @PathVariable("nome") String nomeCliente){
        return String.format("Hello %s", nomeCliente);
    }*/

}
