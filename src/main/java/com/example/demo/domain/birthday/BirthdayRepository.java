package com.example.demo.domain.birthday;

import java.util.List;
import java.util.Optional;

/** 誕生日情報の永続化を抽象化するリポジトリです。 */
public interface BirthdayRepository {
  /**
   * IDで誕生日情報を取得します。
   *
   * @param id 誕生日ID
   * @return 誕生日情報。存在しない場合はnull
   */
  Optional<Birthday> findById(Long id);

  /**
   * すべての誕生日情報を取得します。
   *
   * @return 誕生日情報一覧
   */
  List<Birthday> findAll();

  /**
   * 誕生日情報を保存します。
   *
   * @param birthday 保存対象
   */
  int save(Birthday birthday);

  /**
   * 指定IDの誕生日情報を削除します。
   *
   * @param id 誕生日ID
   */
  void deleteById(Long id);
}
