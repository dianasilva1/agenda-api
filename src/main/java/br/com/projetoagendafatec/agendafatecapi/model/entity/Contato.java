package br.com.projetoagendafatec.agendafatecapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 350, nullable = false)
    private String nome;

    @Column(length = 300, nullable = false)
    private String email;

    @Column(length =15, nullable = false)
    private Integer telefone;

    @Column
    private Boolean favorito;

    @Column
    private byte[] foto;


}
