package com.ti9.send.email.core.domain.service.document;

import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;

import java.util.List;

public interface DocumentService {

    List<DocumentDTO> getDocumentPlaceholderFromDocType(
            List<String> docTypeList,
            List<PaymentStatusEnum> paymentStatusEnumSet
    );

    List<String> getAllDocTypes();
}
