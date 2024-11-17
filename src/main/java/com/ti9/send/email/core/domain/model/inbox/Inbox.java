package com.ti9.send.email.core.domain.model.inbox;


import com.ti9.send.email.core.domain.model.BaseAudit;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.enums.BodyFormatEnum;
import com.ti9.send.email.core.domain.model.enums.PriorityEnum;
import com.ti9.send.email.core.domain.model.inbox.enums.InboxStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ecob_inbox")
public class Inbox extends BaseAudit {
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

    @Column
    private PriorityEnum priority;

    @Column
    private String subject;

    @Column(name = "body_format")
    private BodyFormatEnum bodyFormat;

    @Column(name = "body_text")
    private String bodyText;

    @Column
    private LocalDate scheduled;

    @Column(name = "processed_at")
    private LocalDate processedAt;

    @Column
    private InboxStatusEnum status;

    @Column(name = "error_message")
    private String errorMessage;

    @OneToMany(mappedBy = "inbox", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InboxLink> inboxLinkList;

}
