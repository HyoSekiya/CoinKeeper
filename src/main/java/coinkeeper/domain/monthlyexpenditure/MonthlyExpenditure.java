package coinkeeper.domain.monthlyexpenditure;

import coinkeeper.domain.repository.ExpenditureRepository;

public class MonthlyExpenditure {

    private final ExpenditureRepository expenditureRepository;

    public MonthlyExpenditure(ExpenditureRepository expenditureRepository) {
        this.expenditureRepository = expenditureRepository;
    }

//    public List<MonthlyExpenditureTotal> calculateMonthlyExpenditureTotals() {
//        List<Map<String, Object>> expenditureForAMonth = expenditureRepository.getExpenditureForAMonth();
//        List<MonthlyExpenditureTotal> monthlyExpenditureTotal = new ArrayList<>();
//
//        for (Map<String, Object> value : expenditureForAMonth) {
//            String month = (String) value.get("month");
//            int totalAmount = ((Number) value.get("total_amount")).intValue();
//            monthlyExpenditureTotal.add(new MonthlyExpenditureTotal(month, totalAmount));
//        }
//
//        return monthlyExpenditureTotal;
//    }
}
