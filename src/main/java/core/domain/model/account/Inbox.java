package core.domain.model.account;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ecob_inbox")
public class Inbox {
    @Id
    @Column
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "reply_to")
    private String replyTo;

    @Column(name = "recipients_to")
    private String recipientsTO;

    @Column(name = "recipients_cc")
    private String recipientsCC;


    @Column(name = "recipients_bcc")
    private String recipientsBCC;
}
