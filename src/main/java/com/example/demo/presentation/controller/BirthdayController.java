package com.example.demo.presentation.controller;

import com.example.demo.application.dto.SaveCommand;
import com.example.demo.application.usecase.GetBirthdayUsecase;
import com.example.demo.application.usecase.ListBirthdayUsecase;
import com.example.demo.application.usecase.SaveBirthdayUsecase;
import com.example.demo.domain.birthday.Birthday;
import com.example.demo.presentation.form.BirthdayForm;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 誕生日管理画面の表示と入力処理を担当するコントローラーです。 */
@Controller
@RequestMapping("/birthdays")
public class BirthdayController {

  private final ListBirthdayUsecase listBirthdayUsecase;
  private final GetBirthdayUsecase getBirthdayUsecase;
  private final SaveBirthdayUsecase saveBirthdayUsecase;

  /**
   * コントローラーを初期化します。
   *
   * @param listBirthdayUsecase 一覧取得ユースケース
   * @param saveBirthdayUsecase 保存ユースケース
   */
  public BirthdayController(
      ListBirthdayUsecase listBirthdayUsecase,
      GetBirthdayUsecase getBirthdayUsecase,
      SaveBirthdayUsecase saveBirthdayUsecase) {
    this.listBirthdayUsecase = listBirthdayUsecase;
    this.getBirthdayUsecase = getBirthdayUsecase;
    this.saveBirthdayUsecase = saveBirthdayUsecase;
  }

  /**
   * 誕生日一覧画面を表示します。
   *
   * @param model 画面モデル
   * @return テンプレート名
   */
  @GetMapping
  public String showList(Model model) {
    model.addAttribute("birthdays", listBirthdayUsecase.execute());
    return "birthday-list";
  }

  /**
   * 誕生日詳細画面を表示します。
   *
   * @param id 編集対象ID
   * @param mode 画面モード
   * @param model 画面モデル
   * @return テンプレート名
   */
  @GetMapping("/detail")
  public String showDetail(
      @RequestParam(required = false) Long id,
      @RequestParam(required = false, defaultValue = "edit") String mode,
      Model model) {
    BirthdayForm form;
    if (id != null) {
      Birthday birthday =
          getBirthdayUsecase
              .findById(id)
              .orElseThrow(() -> new IllegalArgumentException("指定された誕生日データが存在しません。"));
      form =
          BirthdayForm.editMode(
              birthday.id(), birthday.name(), birthday.birthDate().toString(), birthday.note());
    } else {
      form = BirthdayForm.addMode();
      if (!"add".equals(mode)) {
        mode = "add";
      }
    }

    model.addAttribute("form", form);
    model.addAttribute("mode", mode);
    return "birthday-detail";
  }

  /**
   * 誕生日情報を保存します。
   *
   * @param id 誕生日ID
   * @param mode 画面モード
   * @param name 名前
   * @param birthDate 生年月日
   * @param note メモ
   * @param model 画面モデル
   * @return 遷移先
   */
  @PostMapping("/save")
  public String save(
      @RequestParam(required = false) Long id,
      @RequestParam(defaultValue = "add") String mode,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String birthDate,
      @RequestParam(required = false) String note,
      Model model) {
    List<String> errors = new ArrayList<>();
    String normalizedMode = normalizeMode(mode);

    if ("edit".equals(normalizedMode) && id == null) {
      errors.add("更新対象のIDが指定されていません。");
    }
    if ("add".equals(normalizedMode) && id != null) {
      errors.add("新規登録ではIDを指定できません。");
    }

    if (name == null || name.isBlank()) {
      errors.add("名前は必須です。");
    }

    LocalDate parsedBirthDate = null;
    if (birthDate == null || birthDate.isBlank()) {
      errors.add("生年月日は必須です。");
    } else {
      try {
        parsedBirthDate = LocalDate.parse(birthDate);
      } catch (DateTimeParseException ex) {
        errors.add("生年月日はYYYY-MM-DD形式で入力してください。");
      }
    }

    BirthdayForm form =
        new BirthdayForm(
            id,
            id == null ? "登録後に自動採番" : String.valueOf(id),
            name,
            birthDate,
            note,
            normalizeMode(mode));

    if (!errors.isEmpty()) {
      model.addAttribute("errors", errors);
      model.addAttribute("form", form);
      model.addAttribute("mode", form.mode());
      return "birthday-detail";
    }

    try {
      saveBirthdayUsecase.execute(new SaveCommand(id, name, parsedBirthDate, note));
    } catch (IllegalArgumentException ex) {
      errors.add(ex.getMessage());
      model.addAttribute("errors", errors);
      model.addAttribute("form", form);
      model.addAttribute("mode", form.mode());
      return "birthday-detail";
    }

    return "redirect:/birthdays";
  }

  private String normalizeMode(String mode) {
    return "edit".equals(mode) ? "edit" : "add";
  }
}
