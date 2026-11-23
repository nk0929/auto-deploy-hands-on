package com.example.demo.application.usecase;

import com.example.demo.application.dto.SaveCommand;
import com.example.demo.domain.birthday.Birthday;
import com.example.demo.domain.birthday.BirthdayRepository;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.stereotype.Service;

/** 誕生日情報を保存するユースケースです。 */
@Service
public class SaveBirthdayUsecase {

  private final BirthdayRepository birthdayRepository;
  private final Clock clock;

  /**
   * ユースケースを初期化します。
   *
   * @param birthdayRepository 誕生日リポジトリ
   */
  public SaveBirthdayUsecase(BirthdayRepository birthdayRepository, Clock clock) {
    this.birthdayRepository = birthdayRepository;
    this.clock = clock;
  }

  /**
   * 誕生日情報を保存します。
   *
   * @param command 保存コマンド
   */
  public void execute(SaveCommand command) {
    SaveCommand request = Objects.requireNonNull(command, "保存コマンドは必須です。");
    validate(request);

    if (request.id() != null && birthdayRepository.findById(request.id()).isEmpty()) {
      throw new IllegalArgumentException("更新対象のデータが存在しません。");
    }

    Birthday birthday =
        new Birthday(request.id(), request.name(), request.birthDate(), request.note());
    int savedCount = birthdayRepository.save(birthday);
    if (request.id() != null && savedCount == 0) {
      throw new IllegalArgumentException("更新対象のデータが存在しません。");
    }
  }

  private void validate(SaveCommand command) {
    if (command.name() == null || command.name().isBlank()) {
      throw new IllegalArgumentException("名前は必須です。");
    }
    if (command.birthDate() == null) {
      throw new IllegalArgumentException("生年月日は必須です。");
    }
    if (command.birthDate().isAfter(LocalDate.now(clock))) {
      throw new IllegalArgumentException("生年月日に未来日は指定できません。");
    }
  }
}
