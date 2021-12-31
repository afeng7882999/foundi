package net.foundi.admin.generator.entity.query;

import lombok.Data;
import net.foundi.framework.entity.query.Criterion;
import net.foundi.framework.entity.query.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务表Query
 */
@Data
public class GenTableQuery implements Query {

    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String tableName;

    @Criterion(type = Criterion.Type.INNER_LIKE)
    private String tableComment;

    @Criterion(type = Criterion.Type.BETWEEN)
    private List<LocalDateTime> tableCreateTime;
}
