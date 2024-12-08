package com.ti9.send.email.core.application.port.out.document;

import com.ti9.send.email.core.application.port.out.GenericRepository;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.model.document.Cre;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;

import java.util.List;
import java.util.UUID;

public interface CreRepository extends GenericRepository<Cre, UUID> {

    List<DocumentDTO> findCreByDocType(List<String> docTypeList, List<PaymentStatusEnum> paymentStatusEnumSet);

    List<String> findDistinctDocTypes();
}
