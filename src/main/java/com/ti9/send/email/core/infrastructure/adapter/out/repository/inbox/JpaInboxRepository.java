package com.ti9.send.email.core.infrastructure.adapter.out.repository.inbox;

import com.ti9.send.email.core.domain.model.inbox.Inbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaInboxRepository extends JpaRepository<Inbox, UUID> {
    @Query(value = "SELECT i FROM Inbox i WHERE i.account.id in :accountIds ")
    List<Inbox> listByAccountIds(List<UUID> accountIds);
}
