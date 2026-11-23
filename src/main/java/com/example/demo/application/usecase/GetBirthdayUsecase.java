package com.example.demo.application.usecase;

import com.example.demo.domain.birthday.Birthday;
import com.example.demo.domain.birthday.BirthdayRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** 誕生日情報を取得するユースケースです。 */
@Service
public class GetBirthdayUsecase {
  private final BirthdayRepository birthdayRepository;

  /**
   * ユースケースを初期化します。
   *
   * @param birthdayRepository 誕生日リポジトリ
   */
  public GetBirthdayUsecase(BirthdayRepository birthdayRepository) {
    this.birthdayRepository = birthdayRepository;
  }

  /**
   * 詳細画面向けにID指定で誕生日情報を取得します。
   *
   * @param id 誕生日ID
   * @return 誕生日情報。存在しない場合はnull
   */
  public Optional<Birthday> findById(Long id) {
    if (id == null) {
      return Optional.empty();
    }
    return birthdayRepository.findById(id);
  }
}
