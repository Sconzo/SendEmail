package com.ti9.send.email.core.infrastructure.adapter.out.sender;

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
            List<MessageInformationDTO> messageInformationDTOS
    ) {
        try {
            for (Sender<?> sender : senders) {
                sendMessage(sender, messageInformationDTOS);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public <T> void sendMessage(
            Sender<T> sender,
            List<? extends MessageInformationDTO> messageInformationDTOs
    ) throws MessagingException {
        for (MessageInformationDTO messageInformationDTO : messageInformationDTOs) {
            sender.send((T) messageInformationDTO);
        }
    }
}
