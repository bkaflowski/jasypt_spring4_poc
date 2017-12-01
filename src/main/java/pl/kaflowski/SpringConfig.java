package pl.kaflowski;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.StringValueResolver;

@Configuration
public class SpringConfig {

    @Bean
    public static StandardPBEStringEncryptor standardPBEStringEncryptor() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword("password");

        return standardPBEStringEncryptor;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer encryptablePropertyPlaceholderConfigurer(StandardPBEStringEncryptor standardPBEStringEncryptor) {
        return new ExtendedEncryptablePropertySourcesPlaceholderConfigurer(standardPBEStringEncryptor);
    }

    /*
      Based on jasypt-spring31 EncryptablePropertySourcesPlaceholderConfigurer class
     */
    public static class ExtendedEncryptablePropertySourcesPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

        private final StringEncryptor stringEncryptor;

        public ExtendedEncryptablePropertySourcesPlaceholderConfigurer(StringEncryptor stringEncryptor) {
            this.stringEncryptor = stringEncryptor;
        }

        @Override
        protected String convertPropertyValue(String originalValue) {
            if (!PropertyValueEncryptionUtils.isEncryptedValue(originalValue)) {
                return originalValue;
            }
            return PropertyValueEncryptionUtils.decrypt(originalValue,
                    this.stringEncryptor);
        }

        @Override
        protected void doProcessProperties(final ConfigurableListableBeanFactory beanFactoryToProcess, final StringValueResolver valueResolver) {
            super.doProcessProperties(beanFactoryToProcess,
                    new StringValueResolver() {
                        public String resolveStringValue(String strVal) {
                            return convertPropertyValue(valueResolver.resolveStringValue(strVal));
                        }
                    }
            );
        }
    }
}
