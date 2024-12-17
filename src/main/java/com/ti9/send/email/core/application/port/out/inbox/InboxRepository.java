package com.ti9.send.email.core.application.port.out.inbox;

import com.ti9.send.email.core.application.port.out.GenericRepository;
import com.ti9.send.email.core.domain.model.inbox.Inbox;

import java.util.List;
import java.util.UUID;

public interface InboxRepository extends GenericRepository<Inbox, UUID> {
    List<Inbox> listByAccountIds(List<UUID> accountIds);
}
