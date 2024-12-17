package com.ti9.send.email.core.domain.model.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cfr")
public class Cfr {
    @Id
    @Column
    private UUID id;

    @Column(name = "codigo")
    private Integer code;

    @Column(name = "cnpj_cpf")
    private String taxId;

    @Column(name = "nome")
    private String name;

    @Column(name = "contato_cobranca")
    private String billingContact;

    @Column(name = "email_cobranca")
    private String billingEmail;
}
