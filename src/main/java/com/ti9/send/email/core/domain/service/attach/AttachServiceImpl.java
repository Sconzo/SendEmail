package com.ti9.send.email.core.domain.service.attach;

import com.ti9.send.email.core.domain.model.attach.Attach;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AttachServiceImpl implements AttachService {


    @Override
    public Map<UUID, List<Attach>> findAttachByRefIds(List<UUID> refIds) {
        return refIds.stream()
                .collect(Collectors.toMap(refId -> refId, refId -> List.of()));
    }
}
