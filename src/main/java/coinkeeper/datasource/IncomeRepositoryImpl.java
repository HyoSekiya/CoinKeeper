package coinkeeper.datasource;

import coinkeeper.api.income.MonthlyIncomeSummaryForm;
import coinkeeper.domain.entity.income.IncomeEntity;
import coinkeeper.domain.repository.IncomeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
//収入データベースのCRUD実装
public class IncomeRepositoryImpl implements IncomeRepository {
    private final JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public IncomeRepositoryImpl(JdbcTemplate jdbcTemplate,EntityManager entityManager) {
        this.jdbcTemplate = jdbcTemplate;
        this.entityManager = entityManager;
    }

    @Override
    public void insertIncome(IncomeEntity incomeEntity) {

        jdbcTemplate.update("INSERT INTO income(amount, category, categoryMemo, date) values (?, ?, ?, ?)",
                incomeEntity.getAmount(), incomeEntity.getCategory(), incomeEntity.getCategoryMemo(), incomeEntity.getDate());
    }

    @Override
    public int updateIncome(IncomeEntity incomeEntity) {

        return jdbcTemplate.update("UPDATE income SET amount = ?, category = ?, categoryMemo = ? WHERE id = ?",
                incomeEntity.getAmount(), incomeEntity.getCategory(), incomeEntity.getCategoryMemo(), incomeEntity.getId());
    }

    @Override
    public int deleteIncome(int id) {
        return jdbcTemplate.update("DELETE FROM income WHERE id = ?", id);
    }

    @Override
    public List<IncomeEntity> getIncome() {
        String sql = "SELECT id, amount, category, categoryMemo, date FROM income";
        List<Map<String, Object>> incomeList = jdbcTemplate.queryForList(sql);

        List<IncomeEntity> list = new ArrayList<>();

        for (Map<String, Object> result : incomeList) {
            IncomeEntity incomeEntity = new IncomeEntity();
            incomeEntity.setId((int) result.get("id"));
            incomeEntity.setAmount((int) result.get("amount"));
            incomeEntity.setCategory((String) result.get("category"));
            incomeEntity.setCategoryMemo((String) result.get("categoryMemo"));
            incomeEntity.setDate(((Timestamp) result.get("date")).toLocalDateTime());


            list.add(incomeEntity);
        }

        return list;
    }

    @Override
    public Optional<IncomeEntity> findById(int id) {
        String sql = "SELECT id, amount, category, categoryMemo, date FROM income WHERE id = ?";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql,id);

        IncomeEntity incomeEntity = new IncomeEntity();
        incomeEntity.setId((int)result.get("id"));
        incomeEntity.setAmount((int)result.get("amount"));
        incomeEntity.setCategory((String) result.get("category"));
        incomeEntity.setCategoryMemo((String) result.get("categoryMemo"));
        incomeEntity.setDate(((Timestamp) result.get("date")).toLocalDateTime());

        Optional<IncomeEntity> incomeEntityOptional = Optional.ofNullable(incomeEntity);

        return incomeEntityOptional;
    }

    @Override
    public List<MonthlyIncomeSummaryForm> getMonthlyIncomeSummaries(){
        String sql = "SELECT YEAR(`date`) AS `year`, MONTH(`date`) AS `month`, SUM(`amount`) AS totalAmount FROM income GROUP BY YEAR(`date`), MONTH(`date`)";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MonthlyIncomeSummaryForm summary = new MonthlyIncomeSummaryForm();
            summary.setYear(rs.getInt("year"));
            summary.setMonth(rs.getInt("month"));
            summary.setTotalAmount(rs.getInt("totalAmount"));
            return summary;
        });
    }








}
