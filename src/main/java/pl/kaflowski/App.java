package pl.kaflowski;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestSpringConfig.class, TestSpringConfig2.class);

        SomeClass someClass = (SomeClass) applicationContext.getBean("someClass");
        SomeClass someClass2 = (SomeClass) applicationContext.getBean("someClass2");
        System.out.println(someClass);
        System.out.println(someClass2);
    }
}
