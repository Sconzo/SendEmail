package com.ti9.send.email.core.domain.service.attach;

import com.ti9.send.email.core.domain.model.attach.Attach;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AttachLinkService {
    Map<UUID, List<Attach>> findAttachLinkByRefIds(List<UUID> refIds);
}
