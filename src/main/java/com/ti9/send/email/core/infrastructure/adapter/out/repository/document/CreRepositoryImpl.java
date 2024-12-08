package com.ti9.send.email.core.infrastructure.adapter.out.repository.document;

import com.ti9.send.email.core.application.port.out.document.CreRepository;
import com.ti9.send.email.core.domain.model.account.Account;
import com.ti9.send.email.core.domain.model.document.Cre;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CreRepositoryImpl implements CreRepository {

    private final JpaCreRepository jpaRepository;

    public CreRepositoryImpl(JpaCreRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Cre> findById(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Cre save(Cre cre) {
        return jpaRepository.save(cre);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Cre> list() {
        return jpaRepository.findAll();
    }

}
