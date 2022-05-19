package io.github.dougllasfps.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "produto") //embaixo tudo anotação do lombok
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Data //Compila varios desses @ em um apenas!
//nele avi ter @Getter/setters, @ToString@EqualsAndhashcode, e por ai vai.
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;

    @Column(name = "preco_unitario")
    @NotNull(message = "{campo.preco.obrigatorio}")
    private BigDecimal preco;

}
