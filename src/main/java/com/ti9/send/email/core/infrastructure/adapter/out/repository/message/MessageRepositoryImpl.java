package com.ti9.send.email.core.infrastructure.adapter.out.repository.message;

import com.ti9.send.email.core.application.port.out.message.MessageRepository;
import com.ti9.send.email.core.domain.model.message.MessageRule;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MessageRepositoryImpl implements MessageRepository {

    private final JpaMessageRepository jpaRepository;

    public MessageRepositoryImpl(JpaMessageRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<MessageRule> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public MessageRule save(MessageRule messageRule) {
        return jpaRepository.save(messageRule);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<MessageRule> list() {
        return jpaRepository.findAllWithoutRelations();
    }
}
