package com.example.demo.infrastructure.repository;

import com.example.demo.domain.birthday.Birthday;
import com.example.demo.domain.birthday.BirthdayRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

/** SQLite向けの誕生日リポジトリ実装です。 */
@Repository
public class SqliteBirthdayReporitory implements BirthdayRepository {

  private final JdbcClient jdbcClient;

  /**
   * リポジトリを初期化します。
   *
   * @param jdbcTemplate JDBC操作テンプレート
   */
  public SqliteBirthdayReporitory(JdbcClient jdbcClient) {
    this.jdbcClient = jdbcClient;
  }

  @Override
  public Optional<Birthday> findById(Long id) {
    if (id == null) {
      return Optional.empty();
    }
    String sql = "SELECT id, name, birth_date, note FROM birthdays WHERE id = :id";
    return jdbcClient.sql(sql).param("id", id).query(Birthday.class).optional();
  }

  @Override
  public List<Birthday> findAll() {
    String sql = "SELECT id, name, birth_date, note FROM birthdays ORDER BY id";
    return jdbcClient.sql(sql).query(Birthday.class).list();
  }

  @Override
  public int save(Birthday birthday) {
    if (birthday.id() == null) {
      return insert(birthday);
    }
    return update(birthday);
  }

  @Override
  public void deleteById(Long id) {
    if (id == null) {
      return;
    }
    String sql = "DELETE FROM birthdays WHERE id = :id";
    jdbcClient.sql(sql).param("id", id).update();
  }

  private int insert(Birthday birthday) {
    String sql = "INSERT INTO birthdays(name, birth_date, note) VALUES (:name, :birth_date, :note)";
    return jdbcClient
        .sql(sql)
        .param("name", birthday.name())
        .param("birth_date", birthday.birthDate().toString())
        .param("note", birthday.note())
        .update();
  }

  private int update(Birthday birthday) {
    String sql =
        "UPDATE birthdays SET name = :name, birth_date = :birth_date, note = :note WHERE id = :id";
    if (birthday.note() == null) {
      return jdbcClient
          .sql(sql)
          .param("name", birthday.name())
          .param("birth_date", birthday.birthDate().toString())
          .param("note", null)
          .param("id", birthday.id())
          .update();
    }
    return jdbcClient
        .sql(sql)
        .param("name", birthday.name())
        .param("birth_date", birthday.birthDate().toString())
        .param("note", birthday.note())
        .param("id", birthday.id())
        .update();
  }
}
