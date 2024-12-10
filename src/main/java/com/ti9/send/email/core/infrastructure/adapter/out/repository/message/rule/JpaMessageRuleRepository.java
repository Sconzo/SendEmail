package com.ti9.send.email.core.infrastructure.adapter.out.repository.message.rule;

import com.ti9.send.email.core.domain.model.message.MessageRule;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaMessageRuleRepository extends JpaRepository<MessageRule, UUID> {


    @Query("select new com.ti9.send.email.core.domain.model.message.MessageRule(" +
            " m.id," +
            " m.name," +
            " m.selectedDay," +
            " m.status" +
            " ) from MessageRule m")
    List<MessageRule> findAllWithoutRelations();

    @Query(value = "SELECT * FROM ecob_msg mr " +
            " WHERE mr.status = 'ACTIVE' " +
            " AND :currentHourMinute = ANY(mr.selected_times)", nativeQuery = true)
    List<MessageRule> findActiveTemplates(String currentHourMinute);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ecob_msg " +
            " SET model_id = :templateId " +
            " WHERE id = :messageRuleId", nativeQuery = true)
    void setTemplateId(UUID templateId, UUID messageRuleId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ecob_msg " +
            " SET status = CASE " +
            " WHEN status = 'ACTIVE' THEN 'INACTIVE' " +
            " WHEN status = 'INACTIVE' THEN 'ACTIVE' " +
            " END" +
            " WHERE id = :uuid", nativeQuery = true)
    void changeStatus(UUID uuid);
}
