package core.domain.model.account;

import core.domain.model.StatusEnum;
import core.domain.model.UpdatableBaseAudit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ecob_account")
public class Account extends UpdatableBaseAudit {
    @Id
    @Column
    private UUID id;

    @Column
    private String name;

    @Column
    private ProviderEnum provider;

    @Column
    private StatusEnum status;

    @Column
    private String settings;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inbox> inboxList;

}
