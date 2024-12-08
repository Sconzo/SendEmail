package com.ti9.send.email.core.domain.service.document;

import com.ti9.send.email.core.application.port.out.document.CreRepository;
import com.ti9.send.email.core.domain.dto.document.DocumentDTO;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.infrastructure.adapter.out.repository.document.CreRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService{

    private final CreRepository repository;

    public DocumentServiceImpl(CreRepositoryImpl repository) {
        this.repository = repository;
    }


    @Override
    public List<DocumentDTO> getDocumentPlaceholderFromDocType(
            List<String> docTypeList,
            List<PaymentStatusEnum> paymentStatusEnumSet
    ) {
        return repository.findCreByDocType(
                docTypeList,
                paymentStatusEnumSet
        );
    }

    @Override
    public List<String> getAllDocTypes(){
        return repository.findDistinctDocTypes();
    }
}
