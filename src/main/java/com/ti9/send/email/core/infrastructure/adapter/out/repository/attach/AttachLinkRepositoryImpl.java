package com.ti9.send.email.core.infrastructure.adapter.out.repository.attach;

import com.ti9.send.email.core.application.port.out.attach.AttachLinkRepository;
import com.ti9.send.email.core.domain.model.attach.AttachLink;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AttachLinkRepositoryImpl implements AttachLinkRepository {

    private final JpaAttachLinkRepository jpaAttachLinkRepository;

    public AttachLinkRepositoryImpl(JpaAttachLinkRepository jpaAttachLinkRepository) {
        this.jpaAttachLinkRepository = jpaAttachLinkRepository;
    }

    @Override
    public List<AttachLink> findAttachLinkByRefIds(List<UUID> refIds) {
        return jpaAttachLinkRepository.findAttachLinkByRefIds(refIds);
    }
}
