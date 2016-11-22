package uExt.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.Cipher;

public class Auth<T> {

    private Function<T, Boolean> authFn = i->false;

    private static Auth auth = null;

    private static Map<String, Map<String, T>> authMaps;

    public static Auth getInstance(String s) {
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
