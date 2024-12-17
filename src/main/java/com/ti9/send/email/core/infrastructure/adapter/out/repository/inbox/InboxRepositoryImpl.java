package com.ti9.send.email.core.infrastructure.adapter.out.repository.inbox;

import com.ti9.send.email.core.application.port.out.inbox.InboxRepository;
import com.ti9.send.email.core.domain.model.inbox.Inbox;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public class InboxRepositoryImpl implements InboxRepository {

    private final JpaInboxRepository jpaInboxRepository;

    public InboxRepositoryImpl(JpaInboxRepository jpaInboxRepository) {
        this.jpaInboxRepository = jpaInboxRepository;
    }

    @Override
    public Optional<Inbox> findById(UUID uuid) {
        return jpaInboxRepository.findById(uuid);
    }

    @Override
    public Inbox save(Inbox inbox) {
        return jpaInboxRepository.save(inbox);
    }

    @Override
    public void deleteById(UUID uuid) {
        jpaInboxRepository.deleteById(uuid);
    }

    @Override
    public List<Inbox> list() {
        return jpaInboxRepository.findAll();
    }

    @Override
    public List<Inbox> listByAccountIds(List<UUID> accountIds) {
        return jpaInboxRepository.listByAccountIds(accountIds);
    }
}
