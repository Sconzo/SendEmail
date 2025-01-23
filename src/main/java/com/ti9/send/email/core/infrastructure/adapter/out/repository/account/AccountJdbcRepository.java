package com.ti9.send.email.core.infrastructure.adapter.out.repository.account;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@AllArgsConstructor
public class AccountJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void updateAccountSettings(UUID id, String settings) {
        String update = "UPDATE ecob_account SET settings = ?::jsonb WHERE id = ?";
        jdbcTemplate.update(update, settings, id);
    }

}
