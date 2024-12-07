package com.ti9.send.email.core.domain.model.document;

import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "cre")
public class Cre extends UpdatableBaseAudit {
    @Id
    @Column
    private UUID id;

    @Column(name = "filial_id")
    private UUID filialId;

    @Column(name = "client_id")
    private UUID clientId;

    @Column(name = "doc_type")
    private String docType;

    @Column(name = "documento")
    private String document;

    @Column(name = "data_emissao")
    private LocalDate issueDate;

    @Column(name = "data_vencimento")
    private LocalDate dueDate;

    @Column(name = "valor_documento")
    private BigDecimal documentAmount;

    @Column(name = "valor_aberto")
    private BigDecimal outstandingAmount;

    @Column
    private PaymentStatusEnum status;

}
