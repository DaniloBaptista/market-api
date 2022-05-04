package io.github.dougllasfps.rest.controller;

import io.github.dougllasfps.domain.entity.ItemPedido;
import io.github.dougllasfps.domain.entity.Pedido;
import io.github.dougllasfps.domain.repository.ItemsPedido;
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
@RequestMapping("/api/item-pedido")
public class ItemPedidoController {

    private ItemsPedido itemsPedido;

    public ItemPedidoController(ItemsPedido itemsPedido){
        this.itemsPedido = itemsPedido;
    }

    @GetMapping("/{id}")
    public ItemPedido getPedidoById(@PathVariable Integer id){
        return itemsPedido
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "pedidos não encontrado"));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemPedido save( @RequestBody ItemPedido itemPedido){
        return itemsPedido.save(itemPedido);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        itemsPedido.findById(id)
                .map( itemPedido -> {
                    itemsPedido.delete(itemPedido );
                    return itemPedido;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pedido não encontrado") );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update( @PathVariable Integer id, @RequestBody ItemPedido itemPedido) {
        itemsPedido
                .findById(id)
                .map( itemPedidoExistente -> {
                    itemPedido.setId(itemPedidoExistente.getId());
                    itemsPedido.save(itemPedido);
                    return itemPedidoExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pedido não encontrado") );
    }

    @GetMapping
    public ResponseEntity find(ItemPedido filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        List<Pedido> lista = itemsPedido.findAll(example);
        return ResponseEntity.ok(lista);

    }

}
