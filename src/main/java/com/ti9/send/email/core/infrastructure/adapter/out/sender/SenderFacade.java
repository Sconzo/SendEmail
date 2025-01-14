package com.ti9.send.email.core.infrastructure.adapter.out.sender;

import com.ti9.send.email.core.domain.dto.GenericWrapper;
import com.ti9.send.email.core.domain.dto.message.information.MessageInformationDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SenderFacade {

    private final List<Sender<?>> senders;

    public SenderFacade(List<Sender<?>> senders) {
        this.senders = senders;
    }

    public void send(
            MessageInformationDTO messageInformationDTO
    ) {
        try {
            for (Sender<?> sender : senders) {
                sendMessage(sender, new GenericWrapper<>(messageInformationDTO));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public <T> void sendMessage(
            Sender<T> sender,
            GenericWrapper<? extends MessageInformationDTO> messageInformationDTO
    ) throws MessagingException {
            sender.send((T) messageInformationDTO.getValue());
    }
}
