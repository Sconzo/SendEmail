package com.ti9.send.email.core.infrastructure.adapter.out.repository.document;

import com.ti9.send.email.core.domain.dto.document.PlaceholderDataDTO;
import com.ti9.send.email.core.domain.model.enums.PaymentStatusEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public class CreJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public CreJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PlaceholderDataDTO> findCreByDocType(
            Set<String> docTypeList,
            Set<PaymentStatusEnum> paymentStatusEnumSet
    ) {
        String sql = """
            SELECT 
                cre.id,
                cre.doc_type,
                cfr.nome, 
                cfr.contato_cobranca, 
                cre.documento,
                cre.data_emissao,
                cre.data_vencimento,
                cre.valor_aberto,
                cre.valor_documento 
            FROM cre cre
            INNER JOIN cfr cfr ON cfr.id = cre.cliente_id
            WHERE cre.doc_type IN (:docType)
            AND cre.status IN (:paymentStatusEnumSet)
        """;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("docType", docTypeList);
        parameters.addValue("paymentStatusEnumSet", paymentStatusEnumSet.stream().map(PaymentStatusEnum::name).toList());

        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            return new PlaceholderDataDTO(
                    rs.getObject("id", UUID.class),
                    rs.getString("doc_type"),
                    rs.getString("nome"),
                    rs.getString("contato_cobranca"),
                    rs.getString("documento"),
                    rs.getDate("data_emissao").toLocalDate(),
                    rs.getDate("data_vencimento").toLocalDate(),
                    rs.getBigDecimal("valor_aberto"),
                    rs.getBigDecimal("valor_documento")
            );
        });
    }
}

