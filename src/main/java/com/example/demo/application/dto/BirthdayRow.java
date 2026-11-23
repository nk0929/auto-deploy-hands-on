package com.example.demo.application.dto;

import com.example.demo.domain.birthday.Birthday;
import java.time.LocalDate;

/** 一覧画面に表示する誕生日データです。 */
public record BirthdayRow(
    Long id, String name, LocalDate birthDate, String note, int age, long daysUntilBirthday) {

  public static BirthdayRow from(Birthday birthday, LocalDate today) {
    return new BirthdayRow(
        birthday.id(),
        birthday.name(),
        birthday.birthDate(),
        birthday.note(),
        birthday.calculateAge(today),
        birthday.calculateDaysUntilBirthday(today));
  }
}
