package br.com.projetoagendafatec.agendafatecapi.model.api.rest;

import br.com.projetoagendafatec.agendafatecapi.model.entity.Contato;
import br.com.projetoagendafatec.agendafatecapi.model.repository.ContatoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contatos")
@RequiredArgsConstructor

@CrossOrigin("*")
public class ContatoController {

    private final ContatoRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contato save(@RequestBody Contato contato) {
        return repository.save(contato);
    }

     @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Contato> list(
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
          @RequestParam(value = "size", defaultValue = "10") Integer tamanho){
        Sort sort = Sort.by(Sort.Direction.ASC, "nome");
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, sort);
        return repository.findAll(pageRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Contato> list(){
       return repository.findAll();
   }


    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)/*no_content: sem conteúdo*/
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @PatchMapping("{id}")
    public void favorite(@PathVariable Integer id) {
        Optional<Contato> contato = repository.findById(id);
        contato.ifPresent(c -> {
            boolean favorito = c.getFavorito() == Boolean.TRUE;
            c.setFavorito(!favorito);
            repository.save(c);

        });
    }

    @PutMapping("{id}/foto")
    public byte[] addPhoto(@PathVariable Integer id, @RequestParam("foto") Part arquivo) {
        Optional<Contato> contato = repository.findById(id);
        return contato.map(c -> {
            try {
                InputStream inputStream = arquivo.getInputStream();
                byte[] bytes = new byte[(int) arquivo.getSize()];
                IOUtils.readFully(inputStream, bytes);
                c.setFoto(bytes);
                repository.save(c);
                inputStream.close();
                return bytes;
            } catch (IOException ex) {
                return null;
            }
        }).orElse(null);
    }



}



