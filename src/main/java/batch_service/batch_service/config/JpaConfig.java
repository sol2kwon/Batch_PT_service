package batch_service.batch_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing //시간에 대해 자동으로 값을 넣어줌(중복코드 줄일 수 있음)
@Configuration
public class JpaConfig {
}
