package com.ti9.send.email.core.domain.service.inbox;

import com.ti9.send.email.core.domain.model.inbox.Inbox;

import java.util.List;
import java.util.UUID;

public interface InboxService {
    List<Inbox> listInboxByAccountIds(List<UUID> accountIds);
}
