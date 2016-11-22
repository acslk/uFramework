package controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import models.Person;
import u.http.Requests;
import u.http.Responses;
import uExt.auth.Auth;
import uExt.model.Model;
import uExt.routing.Controller;

import java.io.IOException;
import java.util.List;

import static uExt.template.Template.renderHtml;

public class UserController extends Controller {

    Auth<Person> auth;

    public UserController() {
        auth = Auth.get("auth");
        Person test = new Person("Test", "Tmail", "Tword");
        try {
            Model.insert(test);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public FullHttpResponse home() throws IOException {
        return renderHtml("login.html", "");
    }

    public FullHttpResponse login() {
        String name = Requests.getQueryParam(getRequest(), "Name");
        System.out.println("Name " + name);
        FullHttpResponse response = Responses.ok("");
        try {
            List<Person> result = Model.get(Person.class, name, Person.ANY_VALUE, Person.ANY_VALUE);
            if (result.size() > 0) {
                auth.login(result.get(0), response);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Responses.notFound("error");
        }
        return response;
    }

    public FullHttpResponse signup() {
        String name = Requests.getQueryParam(getRequest(), "Name");
        String email = Requests.getQueryParam(getRequest(), "Email");
        String password = Requests.getQueryParam(getRequest(), "Password");

        Person person = new Person(name, email, password);
        FullHttpResponse response = Responses.ok("ok");

        try {
            Model.insert(person);
            System.out.println(person.name);
            auth.login(person, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Responses.notFound("bad");
        }
        return response;
    }

    public FullHttpResponse self() {
        Person p = auth.authenticate(getRequest());
        if (p == null)
            return Responses.notFound("Unauthenticated");
        return Responses.ok("Current user : " + p.name);
    }

    public FullHttpResponse getUser(int i) {
        if (auth.authenticate(getRequest()) == null)
            return Responses.notFound("Unauthenticated");
        try {
            List<Person> all = Model.get(Person.class, Model.ANY_VALUE, Model.ANY_VALUE, Model.ANY_VALUE);
            if (i >= all.size())
                return Responses.ok("maximum id of " + (all.size()-1));
            return Responses.ok(String.format("user %d: %s %s %s", i, all.get(i).name, all.get(i).email, all.get(i).password));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return Responses.notFound("bad");
        }
    }

}
