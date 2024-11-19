package com.ti9.send.email.core.application.port.out;

import com.ti9.send.email.core.domain.model.account.Account;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericRepository<T, ID> {
    Optional<T> findById(ID id);
    T save(T t);
    void deleteById(ID id);
    List<T> list();
}
