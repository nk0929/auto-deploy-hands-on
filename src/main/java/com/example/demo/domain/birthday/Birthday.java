package com.example.demo.domain.birthday;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * 誕生日情報を表すドメインモデルです。
 *
 * <p>年齢および次の誕生日までの日数計算はドメイン責務として提供します。
 */
public record Birthday(Long id, String name, LocalDate birthDate, String note) {

  /** ドメイン不変条件を満たすように値を正規化します。 */
  public Birthday {
    if (id != null && id <= 0) {
      throw new IllegalArgumentException("IDは1以上で指定してください。");
    }
    name = normalizeName(name);
    birthDate = Objects.requireNonNull(birthDate, "生年月日は必須です。");
    note = normalizeNote(note);
  }

  /**
   * 指定日基準の年齢を計算します。
   *
   * @param today 計算基準日
   * @return 年齢
   */
  public int calculateAge(LocalDate today) {
    LocalDate targetDay = Objects.requireNonNull(today, "計算基準日は必須です。");
    return Math.max(0, Period.between(birthDate, targetDay).getYears());
  }

  /**
   * 指定日から次の誕生日までの日数を計算します。
   *
   * @param today 計算基準日
   * @return 誕生日までの日数
   */
  public long calculateDaysUntilBirthday(LocalDate today) {
    LocalDate targetDay = Objects.requireNonNull(today, "計算基準日は必須です。");
    LocalDate nextBirthday = birthdayInYear(targetDay.getYear());
    if (nextBirthday.isBefore(targetDay)) {
      nextBirthday = birthdayInYear(targetDay.getYear() + 1);
    }
    return ChronoUnit.DAYS.between(targetDay, nextBirthday);
  }

  private LocalDate birthdayInYear(int year) {
    int monthValue = birthDate.getMonthValue();
    int dayOfMonth = birthDate.getDayOfMonth();
    int maxDay = Month.of(monthValue).length(Year.isLeap(year));
    return LocalDate.of(year, monthValue, Math.min(dayOfMonth, maxDay));
  }

  private static String normalizeName(String value) {
    String normalized = Objects.requireNonNull(value, "名前は必須です。").trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("名前は必須です。");
    }
    return normalized;
  }

  private static String normalizeNote(String value) {
    if (value == null) {
      return null;
    }
    String normalized = value.trim();
    return normalized.isEmpty() ? null : normalized;
  }
}
