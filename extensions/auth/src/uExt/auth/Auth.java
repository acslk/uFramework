package uExt.auth;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.security.SecureRandom;
import java.math.BigInteger;


public class Auth<T>
{
    private static Map<String, Auth<?>> globalAuth = new HashMap<>();

    public static Auth get(String s) {
        if (!globalAuth.containsKey(s)) {
            Auth auth = new Auth(null, 10000);
            globalAuth.put(s, auth);
        }
        return globalAuth.get(s);
    }


    // default 30 minutes
    public static final long DEFAULT_EXPIRATION = 1800000L;

    private final SecureRandom random;
    private int authenticatedSalt;
    private final Map<String, T> authMap;

    private final long expiration;
    private final Map<String, Long> authTime;

    private final Function<T, Boolean> authFn;

    // expiration 0 means permanent
    public Auth(final Function<T, Boolean> authFn, long expiration)
    {
        this.authFn = authFn;
        this.random = new SecureRandom();
        this.authenticatedSalt = 0;
        this.authMap = new HashMap<>();
        this.authTime = new HashMap<>();
        this.expiration = expiration;
    }

    private void refreshTables()
    {
        final long cur = System.currentTimeMillis();
        final Iterator<Map.Entry<String,Long>> it = authTime.entrySet().iterator();
        while(it.hasNext())
        {
            final Map.Entry<String,Long> entry = it.next();
            if(cur - entry.getValue() >= expiration)
            {
                // if expired, remove from both maps
                authMap.remove(entry.getKey());
                it.remove();
            }
        }
    }

    private String generateToken()
    {
        final String result = Integer.toString(authenticatedSalt) + new BigInteger(130, random).toString(32);
        authenticatedSalt++;
        return result;
    }

    public synchronized void login(T info, FullHttpResponse response)
    {
        if((authFn != null && authFn.apply(info)) || authFn == null)
        {
            final String uToken = generateToken();
            authMap.put(uToken, info);
            authTime.put(uToken, System.currentTimeMillis());
            response.headers().add(HttpHeaderNames.SET_COOKIE, io.netty.handler.codec.http.cookie.ServerCookieEncoder.STRICT.encode("uToken", uToken));
        }
    }

    public synchronized T authenticate(HttpRequest request)
    {
        refreshTables();
        String cookieString = request.headers().get(HttpHeaderNames.COOKIE);
        if (cookieString != null)
        {
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieString);
            if (!cookies.isEmpty())
            {
                for (final Cookie cookie: cookies)
                {
                    if(cookie.name().equals("uToken"))
                    {
                        final T obj = authMap.get(cookie.value());
                        if(obj != null)
                        {
                            // if contains key, then update the authTime map too
                            authTime.put(cookie.value(), System.currentTimeMillis());
                        }
                        return obj;
                    }
                }
            }
        }
        return null;
    }
}
