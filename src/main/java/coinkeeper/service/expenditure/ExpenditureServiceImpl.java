package coinkeeper.service.expenditure;

import coinkeeper.api.expenditure.MonthlyExpenditureSummaryForm;
import coinkeeper.config.CoinkeeperFoundException;
import coinkeeper.domain.entity.expenditure.ExpenditureEntity;
import coinkeeper.domain.entity.expenditure.ExpenditureId;
import coinkeeper.domain.repository.ExpenditureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenditureServiceImpl implements ExpenditureService {

    private final ExpenditureRepository expenditureRepository;

    @Autowired
    ExpenditureServiceImpl(ExpenditureRepository expenditureRepository) {
        this.expenditureRepository = expenditureRepository;
    }

    @Override
    public void insertExpenditure(ExpenditureEntity expenditureEntity) {
        expenditureRepository.insertExpenditure(expenditureEntity);
    }

    @Override
    public void updateExpenditure(ExpenditureEntity expenditureEntity) {
        if(expenditureRepository.updateExpenditure(expenditureEntity) == 0) {
            throw new CoinkeeperFoundException("更新する支出記録がありません");
        }

        expenditureRepository.updateExpenditure(expenditureEntity);
    }

    @Override
    public void deleteExpenditure(int expenditureId) {
        if(expenditureRepository.deleteExpenditure(expenditureId) == 0) {
            throw new CoinkeeperFoundException("削除する支出記録がありません");
        }

        expenditureRepository.deleteExpenditure(expenditureId);
    }

    @Override
    public List<ExpenditureEntity> getExpenditure() {
        return expenditureRepository.getExpenditure();
    }

    @Override
    public Optional<ExpenditureEntity> getExpenditureOptional(int expenditureId) {
        try{
            return expenditureRepository.findById(expenditureId);
        } catch (EmptyResultDataAccessException e){
            throw new CoinkeeperFoundException("指定した支出情報がありません");
        }
    }
    @Override
    public List<MonthlyExpenditureSummaryForm> getMonthlyExpenditureSummary() {
        return expenditureRepository.getMonthlyExpenditureSummaries();
    }

}
