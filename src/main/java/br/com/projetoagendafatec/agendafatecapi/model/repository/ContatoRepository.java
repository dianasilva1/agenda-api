package br.com.projetoagendafatec.agendafatecapi.model.repository;

import br.com.projetoagendafatec.agendafatecapi.model.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato,Integer> {

}
