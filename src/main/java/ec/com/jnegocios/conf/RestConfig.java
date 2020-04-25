package ec.com.jnegocios.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ec.com.jnegocios.util.AppHelper;

@Configuration
public class RestConfig implements WebMvcConfigurer{
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins(AppHelper.CROSS_ORIGIN)
			.allowedMethods("*")
				.allowedHeaders("*")
			.allowCredentials(true).maxAge(3600);
	}
}
