package com.example.demo.application.usecase;

import com.example.demo.application.dto.BirthdayRow;
import com.example.demo.domain.birthday.BirthdayRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

/** 誕生日一覧を取得するユースケースです。 */
@Service
public class ListBirthdayUsecase {

  private final BirthdayRepository birthdayRepository;
  private final Clock clock;

  /**
   * ユースケースを初期化します。
   *
   * @param birthdayRepository 誕生日リポジトリ
   */
  public ListBirthdayUsecase(BirthdayRepository birthdayRepository, Clock clock) {
    this.birthdayRepository = birthdayRepository;
    this.clock = clock;
  }

  /**
   * 一覧画面向けの誕生日情報を取得します。
   *
   * @return 一覧画面向け誕生日情報
   */
  public List<BirthdayRow> execute() {
    LocalDate today = LocalDate.now(clock);
    return birthdayRepository.findAll().stream()
        .map(birthday -> BirthdayRow.from(birthday, today))
        .toList();
  }
}
