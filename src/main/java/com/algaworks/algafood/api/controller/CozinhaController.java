package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.contract.CozinhasXMLContract;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.http.*;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaRepository cozinhaRepository;

    public CozinhaController(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    @GetMapping // collection resource
    public List<Cozinha> listarJSON() {
        // para lista vazia o 200 ainda continua sendo a boa pratica em relacao ao 204
        return cozinhaRepository.buscarTodos();
    }

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE) // content negotiation
    public CozinhasXMLContract listarXML() {
        return new CozinhasXMLContract(cozinhaRepository.buscarTodos());
    }

    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) // singleton resource
    public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
        var cozinha = cozinhaRepository.buscarPorId(id);
        if (Objects.isNull(cozinha)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cozinha);
    }

    @GetMapping("/follow")
    public ResponseEntity<String> testeRedirect(UriComponentsBuilder uriBuilder) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "http://localhost:8081/cozinhas");
        headers.add("X-Custom-Header", UUID.randomUUID().toString());

        return ResponseEntity.status(HttpStatus.FOUND) // 302 sempre retornar o Location
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers)
                .build();
    }

    @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha, UriComponentsBuilder uriBuilder) {
        var salved = cozinhaRepository.salvar(cozinha);

        URI location = uriBuilder.path("/cozinhas/{id}").buildAndExpand(salved.getId()).toUri();
        return ResponseEntity.created(location).body(salved);
    }
}
