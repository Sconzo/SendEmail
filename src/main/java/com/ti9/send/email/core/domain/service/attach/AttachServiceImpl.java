package com.ti9.send.email.core.domain.service.attach;

import com.ti9.send.email.core.domain.model.attach.Attach;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AttachServiceImpl implements AttachService {


    @Override
    public List<Attach> findAttachByRefIds(List<UUID> refIds) {
        return List.of();
    }
}
