package com.ti9.send.email.core.domain.service.document;

import com.ti9.send.email.core.domain.dto.document.PlaceholderDataDTO;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;

import java.util.List;
import java.util.Set;

public interface DocumentService {

    List<PlaceholderDataDTO> getDocumentPlaceholderFromDocType(
            Set<String> docTypeList,
            Set<PaymentStatusEnum> paymentStatusEnumSet
    );

    List<String> getAllDocTypes();
}
