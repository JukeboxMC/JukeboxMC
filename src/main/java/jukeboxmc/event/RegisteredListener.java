package jukeboxmc.event;

import java.lang.reflect.Method;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class RegisteredListener {

    private Method method;
    private Listener listener;

    public RegisteredListener( Method method, Listener listener ) {
        this.method = method;
        this.listener = listener;
    }

    public Method getMethod() {
        return method;
    }

    public Listener getListener() {
        return listener;
    }

}
