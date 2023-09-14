package coinkeeper.api.income;

import coinkeeper.api.income.MonthlyIncomeSummaryForm;
import coinkeeper.domain.entity.income.IncomeEntity;
import coinkeeper.service.income.IncomeService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequestMapping("/income")
//収入api
public class IncomeController {

    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @GetMapping
    public String incomeIndex(Model model) {

        List<IncomeEntity> incomeList = incomeService.getIncome();

        model.addAttribute("incomeList", incomeList);
        model.addAttribute("title", "収入一覧");

        return "income/index_boot";
    }

    //編集画面
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable int id, Model model) {
        Optional<IncomeEntity> incomeEntityOptional = incomeService.getIncomeOptional(id);

        Optional<IncomeForm> incomeFormOptional = incomeEntityOptional.map(t -> makeIncomeForm(t));

        IncomeForm incomeForm = new IncomeForm();

        if(incomeFormOptional.isPresent()) {
            incomeForm = incomeFormOptional.get();
        }
        model.addAttribute("incomeForm", incomeForm);
        model.addAttribute("title", "収入情報の編集");

        return "income/editForm_boot";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute IncomeForm incomeForm,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes){

        IncomeEntity incomeEntity = makeIncomeEntity(incomeForm);

        if(!result.hasErrors()) {
            incomeService.updateIncome(incomeEntity);
            redirectAttributes.addFlashAttribute("complete","編集完了しました");
            return "redirect:/income";
        }

        model.addAttribute("title", "収入情報の編集");
        model.addAttribute("incomeForm",incomeForm);
        return "income/editForm_boot";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id,
                         Model model) {

        incomeService.deleteIncome(id);

        return "redirect:/income";
    }


    @GetMapping("/form")
    public String showIncomeForm(IncomeForm incomeForm,
                                 Model model,
                                 @ModelAttribute("complete") String complete) {
        model.addAttribute("incomeForm", incomeForm);
        model.addAttribute("title","収入入力");
        return "income/form_boot";
    }

    @PostMapping("/form")
    public String incomeFormGoBack(IncomeForm incomeForm,
                                   Model model
                                   ) {
        model.addAttribute("incomeForm", incomeForm);
        model.addAttribute("title","収入入力");
        return "income/form_boot";
    }

    @PostMapping("/confirm")
    public String IncomeConfirm(@Validated IncomeForm incomeForm,
                                BindingResult result,
                                Model model){
        if(result.hasErrors()){
            model.addAttribute("title", "収入入力");
            return "income/form_boot";
        }
        model.addAttribute("incomeForm", incomeForm);
        model.addAttribute("title","収入確認ページ");
        return "income/confirm_boot";
    }

    @PostMapping("/complete")
    public String IncomeComplete(@Validated IncomeForm incomeForm,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        model.addAttribute("incomeForm", incomeForm);
        if (result.hasErrors()) {
            model.addAttribute("title", "収入入力");
            return "income/form_boot";
        }

        IncomeEntity incomeEntity = makeIncomeEntity(incomeForm);

        incomeService.insertIncome(incomeEntity);

        redirectAttributes.addFlashAttribute("complete", "登録完了になります。引き続き入力してください。");
        return "redirect:/income/form";
    }
    @GetMapping("/calculate")
    public String getMonthlyIncomeSummary(Model model) {
        List<MonthlyIncomeSummaryForm> summaryList = incomeService.getMonthlyIncomeSummary();
        model.addAttribute("summaryList", summaryList);
        return "income/incomeForAMonth";
    }


    private IncomeEntity makeIncomeEntity(IncomeForm incomeForm) {
        IncomeEntity incomeEntity = new IncomeEntity();

        incomeEntity.setId(incomeForm.getId());
        incomeEntity.setAmount(incomeForm.getAmount());
        incomeEntity.setCategory(incomeForm.getCategory());
        incomeEntity.setCategoryMemo(incomeForm.getCategoryMemo());
        incomeEntity.setDate(LocalDateTime.now());

        return incomeEntity;

    }

    private IncomeForm makeIncomeForm(IncomeEntity incomeEntity) {

        IncomeForm incomeForm = new IncomeForm();

        incomeForm.setId(incomeEntity.getId());
        incomeForm.setAmount(incomeEntity.getAmount());
        incomeForm.setCategory(incomeEntity.getCategory());
        incomeForm.setCategoryMemo(incomeEntity.getCategoryMemo());


        return incomeForm;
    }
}
