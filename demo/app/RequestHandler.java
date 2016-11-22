import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import models.Person;
import u.http.Responses;
import uExt.model.Model;

import java.util.List;

public class RequestHandler {

    public FullHttpResponse handle(HttpRequest request, HttpContent content) throws IllegalAccessException {

        Person p = new Person("bob", 1);
        Model.insert(p);
        List<Person> persons = Model.get(Person.class, "bob", 1);
        return Responses.ok(persons.size() + persons.get(0).name + persons.get(0).age + "user input");
    }

}
