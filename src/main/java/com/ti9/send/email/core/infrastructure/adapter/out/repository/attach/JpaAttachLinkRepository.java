package com.ti9.send.email.core.infrastructure.adapter.out.repository.attach;

import com.ti9.send.email.core.domain.model.attach.AttachLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaAttachLinkRepository extends JpaRepository<AttachLink, UUID> {

    @Query(value = "SELECT al FROM AttachLink al WHERE al.refId in :refIds ")
    List<AttachLink> findAttachLinkByRefIds(List<UUID> refIds);
}
