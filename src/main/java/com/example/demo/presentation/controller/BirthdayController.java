package com.example.demo.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/birthdays")
public class BirthdayController {

  @GetMapping
  public String showComingSoon() {
    return "coming-soon";
  }
}
