package com.ti9.send.email.core.application.port.out;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T, ID> {
    Optional<T> findById(ID id);
    T save(T t);
    void deleteById(ID id);
    List<T> list();
}
