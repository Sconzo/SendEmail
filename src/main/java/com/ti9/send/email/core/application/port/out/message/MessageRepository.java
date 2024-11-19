package com.ti9.send.email.core.application.port.out.message;

import com.ti9.send.email.core.application.port.out.GenericRepository;
import com.ti9.send.email.core.domain.model.message.Message;

import java.util.UUID;

public interface MessageRepository extends GenericRepository<Message, UUID> {
}
