package com.ti9.send.email.core.domain.model.account;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ti9.send.email.core.domain.dto.account.AccountSettings;
import com.ti9.send.email.core.domain.dto.account.SmtpSettings;
import com.ti9.send.email.core.domain.model.UpdatableBaseAudit;
import com.ti9.send.email.core.domain.model.enums.StatusEnum;
import com.ti9.send.email.core.domain.model.account.enums.ProviderEnum;
import com.ti9.send.email.core.domain.model.inbox.Inbox;
import com.ti9.send.email.core.domain.model.message.template.MessageTemplate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ecob_account")
public class Account extends UpdatableBaseAudit {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private ProviderEnum provider;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column
    @ColumnTransformer(write = "?::jsonb")
    private String settings;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Inbox> inboxList;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MessageTemplate> messageTemplateList;

    public Account(
            UUID id,
            String name,
            ProviderEnum provider,
            StatusEnum status
    ) {
        this.name = name;
        this.id = id;
        this.provider = provider;
        this.status = status;
    }

    public Account(
            UUID id
    ) {
        this.id = id;
    }

    public void update(Account entityUpdated) {
        this.name = entityUpdated.getName();
        this.status = entityUpdated.getStatus();
        this.provider = entityUpdated.getProvider();
        this.settings = entityUpdated.getSettings();
    }

    public String getSettings() {
        return this.settings.substring(0, this.settings.length() - 1).concat(", \"should_encrypt\": false }");
    }

    public AccountSettings getAccountSettings() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (Objects.requireNonNull(this.provider) == ProviderEnum.SMTP) {
                return objectMapper.readValue(this.getSettings(), SmtpSettings.class);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
