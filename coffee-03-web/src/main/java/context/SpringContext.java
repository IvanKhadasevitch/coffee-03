package context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {
    private static volatile ApplicationContext CONTEXT = null;

    public static ApplicationContext initContext() {
        ApplicationContext context = CONTEXT;
        if (context == null) {
            synchronized (SpringContext.class) {
                context = CONTEXT;
                if (context == null) {
                    CONTEXT = context = new ClassPathXmlApplicationContext("beans-web.xml");
                }
            }
        }

        return context;
    }
}
