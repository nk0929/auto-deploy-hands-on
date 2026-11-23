package com.example.demo.application.dto;

import java.time.LocalDate;

/** 保存ユースケースの入力値です。 */
public record SaveCommand(Long id, String name, LocalDate birthDate, String note) {}
