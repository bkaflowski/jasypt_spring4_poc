package pl.kaflowski;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.spring31.properties.EncryptablePropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class EncryptablePropertySourceFactory implements PropertySourceFactory {

    private static final StringEncryptor standardPBEStringEncryptor = createEncryptor();

    private static StringEncryptor createEncryptor() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword("password");

        return standardPBEStringEncryptor;
    }

    public EncryptablePropertySourceFactory() {
    }

    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if(name == null) {
            name = getNameForResource(resource.getResource());
        }
        return new EncryptablePropertiesPropertySource(name, PropertiesLoaderUtils.loadProperties(resource), standardPBEStringEncryptor);
    }

    /**
     * Copied from Spring's ResourcePropertySource class.
     *
     *
     * Return the description for the given Resource; if the description is
     * empty, return the class name of the resource plus its identity hash code.
     * @see org.springframework.core.io.Resource#getDescription()
     */
    private static String getNameForResource(Resource resource) {
        String name = resource.getDescription();
        if (!StringUtils.hasText(name)) {
            name = resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
        }
        return name;
    }

}
