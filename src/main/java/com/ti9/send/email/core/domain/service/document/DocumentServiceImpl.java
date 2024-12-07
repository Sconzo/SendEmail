package com.ti9.send.email.core.domain.service.document;

import com.ti9.send.email.core.domain.dto.document.PlaceholderDataDTO;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import com.ti9.send.email.core.infrastructure.adapter.out.repository.document.CreJdbcRepository;
import com.ti9.send.email.core.infrastructure.adapter.out.repository.document.JpaCreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DocumentServiceImpl implements DocumentService{

    private final CreJdbcRepository jdbcRepository;
    private final JpaCreRepository jpaRepository;

    public DocumentServiceImpl(CreJdbcRepository jdbcRepository, JpaCreRepository jpaRepository) {
        this.jdbcRepository = jdbcRepository;
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<PlaceholderDataDTO> getDocumentPlaceholderFromDocType(
            Set<String> docTypeList,
            Set<PaymentStatusEnum> paymentStatusEnumSet
    ) {
        return jdbcRepository.findCreByDocType(
                docTypeList,
                paymentStatusEnumSet
        );
    }

    @Override
    public List<String> getAllDocTypes(){
        return jpaRepository.findDistinctDocTypes();
    }
}
