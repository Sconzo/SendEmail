package com.ti9.send.email.core.infrastructure.adapter.out.repository.message;

import com.ti9.send.email.core.domain.model.message.MessageRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaMessageRepository extends JpaRepository<MessageRule, UUID> {


    @Query("select new com.ti9.send.email.core.domain.model.message.MessageRule(" +
            " m.id," +
            " m.name," +
            " m.status" +
            " ) from Message m")
    List<MessageRule> findAllWithoutRelations();
}
