package pl.kaflowski;

public class SomeClass {

    private final String field;
    private final String encryptedProperty;

    public SomeClass(String field, String encryptedProperty) {
        this.field = field;
        this.encryptedProperty = encryptedProperty;
    }

    @Override
    public String toString() {
        return "SomeClass{" +
                "field='" + field + '\'' +
                ", encryptedProperty='" + encryptedProperty + '\'' +
                '}';
    }
}
