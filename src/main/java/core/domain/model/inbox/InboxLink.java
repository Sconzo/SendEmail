package core.domain.model.inbox;

import core.domain.model.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ecob_inbox_link")
public class InboxLink {
    @Id
    @Column
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inbox_id")
    private Inbox inbox;

    @Column(name = "ref_id")
    private UUID refId;
}
