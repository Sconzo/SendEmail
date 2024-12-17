package com.ti9.send.email.core.domain.service.attach;

import com.ti9.send.email.core.domain.model.attach.Attach;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AttachService {
    Map<UUID, List<Attach>> findAttachByRefIds(List<UUID> refIds);
}
