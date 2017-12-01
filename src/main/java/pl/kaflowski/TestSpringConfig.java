package pl.kaflowski;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:test.properties", factory = EncryptablePropertySourceFactory.class)
public class TestSpringConfig {


    @Bean
    public SomeClass someClass(final @Value("${some.property}") String value,
                               final @Value("${encrypted.property}") String encryptedProperty) {
        return new SomeClass(value, encryptedProperty);
    }

}
