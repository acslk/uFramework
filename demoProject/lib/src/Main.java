import com.sun.net.httpserver.*;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

public class Main
{
  public static boolean loggedIn = false;
  public static List<String> posts = new ArrayList<>();

  public static class ParameterFilter extends Filter {

    @Override
    public String description() {
      return "Parses the requested URI for parameters";
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain)
            throws IOException {
      parseGetParameters(exchange);
      parsePostParameters(exchange);
      chain.doFilter(exchange);
    }

    private void parseGetParameters(HttpExchange exchange)
            throws UnsupportedEncodingException {

      Map<String, Object> parameters = new HashMap<>();
      URI requestedUri = exchange.getRequestURI();
      String query = requestedUri.getRawQuery();
      parseQuery(query, parameters);
      exchange.setAttribute("parameters", parameters);
    }

    private void parsePostParameters(HttpExchange exchange)
            throws IOException {

      if ("post".equalsIgnoreCase(exchange.getRequestMethod())) {
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters =
                (Map<String, Object>)exchange.getAttribute("parameters");
        InputStreamReader isr =
                new InputStreamReader(exchange.getRequestBody(),"utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        parseQuery(query, parameters);
      }
    }

    @SuppressWarnings("unchecked")
    private void parseQuery(String query, Map<String, Object> parameters)
            throws UnsupportedEncodingException {

      if (query != null) {
        String pairs[] = query.split("[&]");

        for (String pair : pairs) {
          String param[] = pair.split("[=]");

          String key = null;
          String value = null;
          if (param.length > 0) {
            key = URLDecoder.decode(param[0],
                    System.getProperty("file.encoding"));
          }

          if (param.length > 1) {
            value = URLDecoder.decode(param[1],
                    System.getProperty("file.encoding"));
          }

          if (parameters.containsKey(key)) {
            Object obj = parameters.get(key);
            if(obj instanceof List<?>) {
              List<String> values = (List<String>)obj;
              values.add(value);
            } else if(obj instanceof String) {
              List<String> values = new ArrayList<String>();
              values.add((String)obj);
              values.add(value);
              parameters.put(key, values);
            }
          } else {
            parameters.put(key, value);
          }
        }
      }
    }
  }

  public static void main(String[] args) throws Exception {
    HttpServer server = HttpServer.create(new InetSocketAddress(9000), 0);
    server.createContext("/index", new HomeHandler());
    server.createContext("/login", new LoginHandler());
    server.createContext("/logout", new LogoutHandler());
    server.createContext("/account/post/1", new GetPostHandler());
    server.createContext("/account/posts", new GetPostsHandler());
    HttpContext context = server.createContext("/account/addpost", new AddPostHandler());
    context.getFilters().add(new ParameterFilter());
    server.setExecutor(null);
    server.start();
  }

  static class HomeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      String message = "home";
      t.sendResponseHeaders(200, message.length());
      OutputStream os = t.getResponseBody();
      os.write(message.getBytes());
      os.close();
    }
  }

  static class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      String message = "logged in as User A";
      t.sendResponseHeaders(200, message.length());
      OutputStream os = t.getResponseBody();
      os.write(message.getBytes());
      os.close();
      loggedIn = true;
    }
  }

  static class LogoutHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      String message = "logged out";
      t.sendResponseHeaders(200, message.length());
      OutputStream os = t.getResponseBody();
      os.write(message.getBytes());
      os.close();
      loggedIn = false;
    }
  }

  static class AddPostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      String message;
      if (loggedIn) {
        Map<String, Object> params = (Map<String, Object>)t.getAttribute("parameters");
        posts.add((String)params.get("title"));
        message = "added post titled " + params.get("title");
        t.sendResponseHeaders(200, message.length());
      } else {
        message = "not authenticated";
        t.sendResponseHeaders(401, message.length());
      }
      OutputStream os = t.getResponseBody();
      os.write(message.getBytes());
      os.close();
    }
  }

  static class GetPostsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      String message = "{";
      if (loggedIn) {
        for (String post : posts) {
          message += post;
          message += ",";
        }
        message = message.substring(0, message.length()-1);
        if (posts.size() != 0) {
          message += "}";
        }
        t.sendResponseHeaders(200, message.length());
      } else {
        message = "not authenticated";
        t.sendResponseHeaders(401, message.length());
      }
      OutputStream os = t.getResponseBody();
      os.write(message.getBytes());
      os.close();
    }
  }

  static class GetPostHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException
    {
      String message = "";
      if (loggedIn) {
        if (posts.size() < 1) {
          message = "no post found";
        } else {
          message += posts.get(0);
        }
        t.sendResponseHeaders(200, message.length());
      } else {
        message = "not authenticated";
        t.sendResponseHeaders(401, message.length());
      }
      OutputStream os = t.getResponseBody();
      os.write(message.getBytes());
      os.close();
    }
  }
}
