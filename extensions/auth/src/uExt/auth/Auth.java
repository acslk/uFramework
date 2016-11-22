package uExt.auth;

import java.util.function.Function;

public class Auth<T> {

    private Function<T, Boolean> authFn = i->false;

    private static Auth auth = null;

    public static Auth getInstance(String s) {
        Class c;
        c.asSubclass()
        if (auth == null)
            auth = new Auth();
        return auth;
    }

    public static Auth createInstance() {

    }

    public void register(T info) {
    }

    public void setAuthFn(Function<T, Boolean> authFn) {
        this.authFn = authFn;
    }


    public T authenticate() {

    }

}
