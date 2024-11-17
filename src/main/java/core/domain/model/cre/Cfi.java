package core.domain.model.cre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cfr")
public class Cfi {
    @Id
    @Column
    private UUID id;

    @Column(name = "codigo")
    private Integer code;

    @Column(name = "cnpj_cpf")
    private String taxId;

    @Column(name = "nome")
    private String name;
}
