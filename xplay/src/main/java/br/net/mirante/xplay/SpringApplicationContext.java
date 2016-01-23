package br.net.mirante.xplay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(basePackages = "br.net.mirante")
public class SpringApplicationContext {
	@Bean
    public WicketApplication application() {
        return new WicketApplication();
    }
}
