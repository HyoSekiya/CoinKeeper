package coinkeeper.domain.repository;

import coinkeeper.api.expenditure.ExpenditureForm;
import coinkeeper.api.expenditure.MonthlyExpenditureSummaryForm;
import coinkeeper.domain.entity.expenditure.ExpenditureEntity;
import coinkeeper.domain.entity.expenditure.ExpenditureId;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ExpenditureRepository {

    void insertExpenditure(ExpenditureEntity expenditureEntity);

    int updateExpenditure(ExpenditureEntity expenditureEntity);

    int deleteExpenditure(int id);

    List<ExpenditureEntity> getExpenditure();

    Optional<ExpenditureEntity> findById(int expenditureId);

    List<Map<String, Object>> getExpenditureForAMonth();

    List<MonthlyExpenditureSummaryForm> getMonthlyExpenditureSummaries();
}
