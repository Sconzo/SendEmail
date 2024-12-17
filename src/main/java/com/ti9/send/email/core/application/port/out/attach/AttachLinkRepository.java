package com.ti9.send.email.core.application.port.out.attach;

import com.ti9.send.email.core.domain.model.attach.AttachLink;

import java.util.List;
import java.util.UUID;

public interface AttachLinkRepository {
    List<AttachLink> findAttachLinkByRefIds(List<UUID> refIds);
}
