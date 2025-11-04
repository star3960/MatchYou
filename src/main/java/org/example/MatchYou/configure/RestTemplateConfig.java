package org.example.MatchYou.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate geminiTemplate() {
        RestTemplate template = new RestTemplate();
        template.getInterceptors().add(((request, body, execution) -> execution.execute(request, body)));

        return template;
    }

}
