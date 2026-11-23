package com.example.demo.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** 画面遷移時の例外を共通処理するハンドラーです。 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /** 指定された更新対象が存在しない場合は404として扱います。 */
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handleNotFound(IllegalArgumentException ex, Model model) {
    model.addAttribute("message", ex.getMessage());
    return "error";
  }

  /** IDや数値パラメータの型が不正な場合はクライアント入力エラーとして扱います。 */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleTypeMismatch(MethodArgumentTypeMismatchException ex, Model model) {
    model.addAttribute("message", "入力パラメータが正しくありません。");
    return "error";
  }

  /** 不足パラメータが直接リクエストされた場合もクライアント入力エラーにします。 */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleMissingParameter(MissingServletRequestParameterException ex, Model model) {
    model.addAttribute("message", "入力パラメータが不足しています。");
    return "error";
  }

  /**
   * 想定外例外をエラー画面へ遷移させます。
   *
   * @param ex 発生した例外
   * @param model 画面モデル
   * @return テンプレート名
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleException(Exception ex, Model model) {
    model.addAttribute("message", "処理を完了できませんでした。");
    return "error";
  }
}
