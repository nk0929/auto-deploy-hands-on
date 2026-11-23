package com.example.demo.infrastructure.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** アプリケーション全体で使用するClockを提供する設定クラスです。 */
@Configuration
public class ClockConfig {
  // ** アプリケーション全体で使用するClockを提供するBeanです。 */
  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }
}
