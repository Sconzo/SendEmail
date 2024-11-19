package com.ti9.send.email.core.infrastructure.adapter.out.repository.message;

import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaMessageRepository extends JpaRepository<Message, UUID> {


    @Query("com.ti9.send.email.core.domain.model.message.Message(" +
            " m.id, " +
            " m.name, " +
            " m.status " +
            " ) from Message m ")
    List<Message> findAllWithoutRelations();
}
