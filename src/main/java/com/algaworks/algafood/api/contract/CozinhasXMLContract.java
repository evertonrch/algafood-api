package com.algaworks.algafood.api.contract;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

// Modelo da representacao do recurso de cozinhas em XML
@JacksonXmlRootElement(localName = "cozinhas")
public class CozinhasXMLContract { // modelo de representacao do recurso de cozinha (listagem de cozinha)

    @JacksonXmlProperty(localName = "cozinha")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Cozinha> cozinhas;

    public CozinhasXMLContract(List<Cozinha> cozinhas) {
        this.cozinhas = cozinhas;
    }

    public List<Cozinha> getCozinhas() {
        return cozinhas;
    }
}
