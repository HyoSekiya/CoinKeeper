package coinkeeper.datasource;

import coinkeeper.api.expenditure.MonthlyExpenditureSummaryForm;
import coinkeeper.domain.entity.expenditure.*;
import coinkeeper.domain.entity.expenditure.ExpenditureEntity;
import coinkeeper.domain.entity.expenditure.ExpenditureEntity;
import coinkeeper.domain.repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
//支出データベースのCRUD実装
public class ExpenditureRepositoryImpl implements ExpenditureRepository {
    private final JdbcTemplate jdbcTemplate;

    private final LocalDateTime date = LocalDateTime.now();

    @Autowired
    public ExpenditureRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void insertExpenditure(ExpenditureEntity expenditureEntity) {

        jdbcTemplate.update("INSERT INTO expenditure(amount, category, categoryMemo, date) values (?, ?, ?, ?)",
                expenditureEntity.getExpenditureAmount(), expenditureEntity.getExpenditureCategory(), expenditureEntity.getExpenditureCategoryMemo(), expenditureEntity.getDate());
    }

    @Override
    public int updateExpenditure(ExpenditureEntity expenditureEntity) {

        return jdbcTemplate.update("UPDATE expenditure SET amount = ?, category = ?, categoryMemo = ? WHERE id = ?",
                expenditureEntity.getExpenditureAmount(), expenditureEntity.getExpenditureCategory(), expenditureEntity.getExpenditureCategoryMemo(), expenditureEntity.getExpenditureId());
    }

    @Override
    public int deleteExpenditure(int expenditureId) {
        return jdbcTemplate.update("DELETE FROM expenditure WHERE id = ?", expenditureId);
    }


//    @Override
//    public List<ExpenditureEntity> getExpenditure() {
//        String sql = "SELECT id, amount, category, categoryMemo, date FROM expenditure";
//        List<Map<String, Object>> expenditureList = jdbcTemplate.queryForList(sql);
//        List<ExpenditureEntity> list = new ArrayList<>();
//
//        for (Map<String, Object> result : expenditureList) {
//
//            ExpenditureEntity expenditureEntity = new ExpenditureEntity(
//                    new ExpenditureId((int) result.get("id")),
//                    new ExpenditureAmount((int) result.get("amount")),
//                    new ExpenditureCategory((String)result.get("category")),
//                    new ExpenditureCategoryMemo((String)result.get("categoryMemo")),
//                    ((Timestamp) result.get("date")).toLocalDateTime());
//
//            list.add(expenditureEntity);
////            System.out.println(expenditureEntity.getExpenditureId());
//        }
//
//        return list;
//    }

    @Override
    public List<ExpenditureEntity> getExpenditure() {
        String sql = "SELECT id, amount, category, categoryMemo, date FROM expenditure";
        List<Map<String, Object>> expenditureList = jdbcTemplate.queryForList(sql);

        List<ExpenditureEntity> list = new ArrayList<>();

        for (Map<String, Object> result : expenditureList) {
            ExpenditureEntity expenditureEntity = new ExpenditureEntity();
            expenditureEntity.setExpenditureId((int) result.get("id"));
            expenditureEntity.setExpenditureAmount((int) result.get("amount"));
            expenditureEntity.setExpenditureCategory((String) result.get("category"));
            expenditureEntity.setExpenditureCategoryMemo((String) result.get("categoryMemo"));
            expenditureEntity.setDate(((Timestamp) result.get("date")).toLocalDateTime());


            list.add(expenditureEntity);
        }

        return list;
    }

//    @Override
//    public Optional<ExpenditureEntity> findById(int expenditureId) {
//        String sql = "SELECT id, amount, category, categoryMemo, date FROM expenditure WHERE id = ?";
//
//        Map<String, Object> result = jdbcTemplate.queryForMap(sql,expenditureId);
////        System.out.println(result);
//
//        ExpenditureEntity expenditureEntity = new ExpenditureEntity(
//                new ExpenditureId((int) result.get("id")),
//                new ExpenditureAmount((int) result.get("amount")),
//                new ExpenditureCategory((String)result.get("category")),
//                new ExpenditureCategoryMemo((String)result.get("categoryMemo")),
//                ((Timestamp) result.get("date")).toLocalDateTime());
//
//        Optional<ExpenditureEntity> expenditureEntityOptional = Optional.of(expenditureEntity);
//
//        return expenditureEntityOptional;
//    }

    @Override
    public Optional<ExpenditureEntity> findById(int id) {
        String sql = "SELECT id, amount, category, categoryMemo, date FROM expenditure WHERE id = ?";

        Map<String, Object> result = jdbcTemplate.queryForMap(sql,id);

        ExpenditureEntity expenditureEntity = new ExpenditureEntity();
        expenditureEntity.setExpenditureId((int)result.get("id"));
        expenditureEntity.setExpenditureAmount((int)result.get("amount"));
        expenditureEntity.setExpenditureCategory((String) result.get("category"));
        expenditureEntity.setExpenditureCategoryMemo((String) result.get("categoryMemo"));
        expenditureEntity.setDate(((Timestamp) result.get("date")).toLocalDateTime());

        Optional<ExpenditureEntity> expenditureEntityOptional = Optional.ofNullable(expenditureEntity);

        return expenditureEntityOptional;
    }

    @Override
    public List<Map<String, Object>> getExpenditureForAMonth() {
        String sql = "SELECT CONCAT(EXTRACT(YEAR FROM date), '-', LPAD(EXTRACT(MONTH FROM date), 2, '0')) AS month, SUM(amount) AS totalAmount\n" +
                "FROM expenditure" +
                "GROUP BY EXTRACT(YEAR FROM date), EXTRACT(MONTH FROM date)";
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<MonthlyExpenditureSummaryForm> getMonthlyExpenditureSummaries() {
        String sql = "SELECT YEAR(`date`) AS `year`, MONTH(`date`) AS `month`, SUM(`amount`) AS totalAmount FROM expenditure GROUP BY YEAR(`date`), MONTH(`date`)";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MonthlyExpenditureSummaryForm summary = new MonthlyExpenditureSummaryForm();
            summary.setYear(rs.getInt("year"));
            summary.setMonth(rs.getInt("month"));
            summary.setTotalAmount(rs.getInt("totalAmount"));
            return summary;
        });
    }
}
