package com.ti9.send.email.core.infrastructure.adapter.out.repository.document;

import com.ti9.send.email.core.domain.model.document.Cre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaCreRepository extends JpaRepository<Cre, UUID> {

    @Query(value = "SELECT DISTINCT cre.doc_type from cre cre" +
            " ORDER BY cre.doc_type asc", nativeQuery = true)
    List<String> findDistinctDocTypes();
}
