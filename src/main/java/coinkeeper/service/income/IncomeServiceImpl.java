package coinkeeper.service.income;

import coinkeeper.api.income.MonthlyIncomeSummaryForm;
import coinkeeper.config.CoinkeeperFoundException;
import coinkeeper.domain.entity.income.IncomeEntity;
import coinkeeper.domain.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeServiceImpl implements IncomeService{

    private final IncomeRepository incomeRepository;

    @Autowired
    IncomeServiceImpl(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @Override
    public void insertIncome(IncomeEntity incomeEntity) {
        incomeRepository.insertIncome(incomeEntity);
    }

    @Override
    public void updateIncome(IncomeEntity incomeEntity) {
        if(incomeRepository.updateIncome(incomeEntity) == 0) {
            throw new CoinkeeperFoundException("更新する収入記録がありません");
        }

        incomeRepository.updateIncome(incomeEntity);
    }

    @Override
    public void deleteIncome(int id) {
        if(incomeRepository.deleteIncome(id) == 0) {
            throw new CoinkeeperFoundException("削除する収入記録がありません");
        }

        incomeRepository.deleteIncome(id);
    }

    @Override
    public List<IncomeEntity> getIncome() {
        return incomeRepository.getIncome();
    }

    @Override
    public Optional<IncomeEntity> getIncomeOptional(int id) {
        try{
            return incomeRepository.findById(id);
        } catch (EmptyResultDataAccessException e){
            throw new CoinkeeperFoundException("指定した収入情報がありません");
        }
    }

    @Override
    public List<MonthlyIncomeSummaryForm> getMonthlyIncomeSummary() {
        return incomeRepository.getMonthlyIncomeSummaries();
    }

}
