package com.example.demo.presentation.form;

/** 誕生日編集フォームの画面モデルです。 */
public record BirthdayForm(
    Long id, String idDisplay, String name, String birthDate, String note, String mode) {

  /** 新規登録モードのBirthdayFormを生成します。 */
  public static BirthdayForm addMode() {
    return new BirthdayForm(null, "登録後に自動採番", "", "", "", "add");
  }

  /** 編集モードのBirthdayFormを生成します。 */
  public static BirthdayForm editMode(Long id, String name, String birthDate, String note) {
    return new BirthdayForm(id, String.valueOf(id), name, birthDate, note, "edit");
  }
}
