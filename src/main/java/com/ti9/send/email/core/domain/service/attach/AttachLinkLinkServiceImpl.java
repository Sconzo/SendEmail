package com.ti9.send.email.core.domain.service.attach;

import com.ti9.send.email.core.application.port.out.attach.AttachLinkRepository;
import com.ti9.send.email.core.domain.model.attach.Attach;
import com.ti9.send.email.core.domain.model.attach.AttachLink;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AttachLinkLinkServiceImpl implements AttachLinkService {

    private final AttachLinkRepository attachLinkRepository;

    @Override
    public Map<UUID, List<Attach>> findAttachLinkByRefIds(List<UUID> refIds) {
        List<AttachLink> attachLinkList = attachLinkRepository.findAttachLinkByRefIds(refIds);
        return attachLinkList.stream()
                .collect(Collectors.groupingBy(
                        AttachLink::getRefId,
                        Collectors.mapping(
                                AttachLink::getAttach,
                                Collectors.toList()
                        )
                ));
    }
}
