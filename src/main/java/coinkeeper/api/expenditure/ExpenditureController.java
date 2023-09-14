package coinkeeper.api.expenditure;

import coinkeeper.domain.entity.expenditure.ExpenditureEntity;
import coinkeeper.domain.repository.ExpenditureRepository;
import coinkeeper.service.expenditure.ExpenditureService;
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
@RequestMapping("/expenditure")
//支出api
public class ExpenditureController {

    private final ExpenditureService expenditureService;
    private final ExpenditureRepository expenditureRepository;
    private final LocalDateTime date = LocalDateTime.now();

    public ExpenditureController(ExpenditureService expenditureService, ExpenditureRepository expenditureRepository) {
        this.expenditureRepository = expenditureRepository;
        this.expenditureService = expenditureService;
    }

    //集計画面
    @GetMapping
    public String expenditureIndex(Model model) {
        //テーブルに入っている情報を取得
        List<ExpenditureEntity> expenditureList = expenditureService.getExpenditure();

        //Thymeleafに印字
        model.addAttribute("expenditureList", expenditureList);
        model.addAttribute("title", "支出一覧");


//        System.out.println(expenditureList);

        //集計ページを出力
        return "expenditure/index_boot";
    }

    //編集画面
    @GetMapping("/{expenditureId}/edit")

    //編集時に渡すidの型と、Controllerで使っているExpenditureIdの方が違うことで受け渡しミスが起きている
    public String showEditForm(@PathVariable int expenditureId,Model model) {

        //編集するデータを一件参照する
        Optional<ExpenditureEntity> expenditureEntityOptional = expenditureService.getExpenditureOptional(expenditureId);

        //編集するデータをFormクラスに代入する
        Optional<ExpenditureForm> expenditureFormOptional = expenditureEntityOptional.map(t -> makeExpenditureForm(t));

        ExpenditureForm expenditureForm = new ExpenditureForm();

        if (expenditureFormOptional.isPresent()) {
            //Formの値が存在する場合
            expenditureForm = expenditureFormOptional.get();

        }

        model.addAttribute("expenditureForm", expenditureForm);
        model.addAttribute("title", "支出情報の編集");

        return "expenditure/editForm_boot";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute ExpenditureForm expenditureForm,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes){

        ExpenditureEntity expenditureEntity = makeExpenditureEntity(expenditureForm);

        if(!result.hasErrors()) {
            expenditureService.updateExpenditure(expenditureEntity);
            redirectAttributes.addFlashAttribute("complete","編集完了しました");
            return "redirect:/expenditure";
        }

        model.addAttribute("title", "収入情報の編集");
        model.addAttribute("expenditureForm",expenditureForm);
        return "expenditure/editForm_boot";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int expenditureId,
                         Model model) {

        expenditureService.deleteExpenditure(expenditureId);

        return "redirect:/expenditure";
    }


    @GetMapping("/form")
    public String showExpenditureForm(ExpenditureForm expenditureForm,
                                 Model model,
                                 @ModelAttribute("complete") String complete) {
        model.addAttribute("expenditureForm", expenditureForm);
        model.addAttribute("title","支出入力");
        return "expenditure/form_boot";
    }

    @PostMapping("/form")
    public String expenditureFormGoBack(ExpenditureForm expenditureForm,
                                   Model model
    ) {
        model.addAttribute("expenditureForm", expenditureForm);
        model.addAttribute("title","支出入力");
        return "expenditure/form_boot";
    }

    @PostMapping("/confirm")
    public String ExpenditureConfirm(@Validated ExpenditureForm expenditureForm,
                                BindingResult result,
                                Model model){
        if(result.hasErrors()){
            model.addAttribute("title", "支出入力");
            return "expenditure/form_boot";
        }
        model.addAttribute("expenditureForm", expenditureForm);
        model.addAttribute("title","支出確認ページ");
        return "expenditure/confirm_boot";
    }

    @PostMapping("/complete")
    public String ExpenditureComplete(@Validated ExpenditureForm expenditureForm,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        model.addAttribute("expenditureForm", expenditureForm);
        if (result.hasErrors()) {
            model.addAttribute("title", "支出入力");
            return "expenditure/form_boot";
        }



        ExpenditureEntity expenditureEntity = makeExpenditureEntity(expenditureForm);

        expenditureService.insertExpenditure(expenditureEntity);

        redirectAttributes.addFlashAttribute("complete", "登録完了になります。引き続き入力してください。");
        return "redirect:/expenditure/form";
    }

    @GetMapping("/calculate")
    public String getMonthlyExpenditureSummary(Model model) {
        List<MonthlyExpenditureSummaryForm> summaryList = expenditureService.getMonthlyExpenditureSummary();
        model.addAttribute("summaryList", summaryList);
        return "expenditure/expenditureForAMonth"; // ビューの名前を適宜変更してください
    }



    private ExpenditureEntity makeExpenditureEntity(ExpenditureForm expenditureForm) {
        ExpenditureEntity expenditureEntity = new ExpenditureEntity();

        expenditureEntity.setExpenditureId(expenditureForm.getExpenditureId());
        expenditureEntity.setExpenditureAmount(expenditureForm.getExpenditureAmount());
        expenditureEntity.setExpenditureCategory(expenditureForm.getExpenditureCategory());
        expenditureEntity.setExpenditureCategoryMemo(expenditureForm.getExpenditureCategoryMemo());
        expenditureEntity.setDate(LocalDateTime.now());

        return expenditureEntity;

    }

    private ExpenditureForm makeExpenditureForm(ExpenditureEntity expenditureEntity) {

        ExpenditureForm expenditureForm = new ExpenditureForm();

        expenditureForm.setExpenditureId(expenditureEntity.getExpenditureId());
        expenditureForm.setExpenditureAmount(expenditureEntity.getExpenditureAmount());
        expenditureForm.setExpenditureCategory(expenditureEntity.getExpenditureCategory());
        expenditureForm.setExpenditureCategoryMemo(expenditureEntity.getExpenditureCategoryMemo());


        return expenditureForm;
    }
}
