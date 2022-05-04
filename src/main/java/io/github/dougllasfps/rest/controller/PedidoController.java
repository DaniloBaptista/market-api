package io.github.dougllasfps.rest.controller;

import io.github.dougllasfps.domain.entity.Pedido;
import io.github.dougllasfps.domain.repository.Pedidos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController//com essa annotation todos os métodos já ficam com response body.
@RequestMapping("/api/pedidos")
public class PedidoController {

    private Pedidos pedidos;

    public PedidoController(Pedidos pedidos){
        this.pedidos = pedidos;
    }

    @GetMapping("/{id}")
    public Pedido getPedidoById(@PathVariable Integer id){
        return pedidos
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "pedidos não encontrado"));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido save( @RequestBody Pedido pedido){
        return pedidos.save(pedido);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        pedidos.findById(id)
                .map( pedido -> {
                    pedidos.delete(pedido );
                    return pedido;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pedido não encontrado") );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody Pedido pedido) {
        pedidos
                .findById(id)
                .map( pedidoExistente -> {
                    pedido.setId(pedidoExistente.getId());
                    pedidos.save(pedido);
                    return pedidoExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pedido não encontrado") );
    }

    @GetMapping
    public ResponseEntity find(Pedido filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Pedido> lista = pedidos.findAll(example);
        return ResponseEntity.ok(lista);

    }

}
