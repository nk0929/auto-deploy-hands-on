package com.example.demo.presentation.controller;

import com.example.demo.application.usecase.ListBirthdayUsecase;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/birthdays")
public class BirthdayController {

  private final ListBirthdayUsecase listBirthdayUsecase;

  public BirthdayController(ListBirthdayUsecase listBirthdayUsecase) {
    this.listBirthdayUsecase = listBirthdayUsecase;
  }

  @GetMapping
  public String showList(Model model) {
    model.addAttribute("birthdays", listBirthdayUsecase.execute());
    return "birthday-list";
  }

  @GetMapping("/detail")
  public String showDetail() {
    return "coming-soon";
  }
}
