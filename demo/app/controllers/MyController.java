package controllers;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;
import models.Person;
import u.http.Requests;
import u.http.Responses;
import uExt.auth.Auth;
import uExt.routing.Controller;

import java.io.IOException;

import static uExt.template.Template.renderHtml;
import static u.http.Responses.ok;

public class MyController extends Controller {

//    Auth<Person> auth;
//
//    public MyController () {
//        auth = Auth.get("auth");
//    }

    //GET
    public FullHttpResponse home() throws IOException {
//        try {
//        boolean loggedIn = false;
//        String name = "";
//        Person p = auth.authenticate(getRequest());
//        if (p != null) {
//            loggedIn = true;
//            name = p.name;
//        }
        String message = Requests.getQueryParam(getRequest(), "message");
        if (message == null)
            message = "No message set";
//            return renderHtml("index.html", String.format("{\"message\" : \"%s\", \"loggedin\" : %b, \"user\" : \"%s\"}", message, loggedIn, name));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Responses.ok("failed");
        return renderHtml("index.html", message);
    }

    public FullHttpResponse printClientById(int id){
        return ok("clientId is: " + id);
    }

    public FullHttpResponse loginpage() throws IOException {
        return renderHtml("login.html", "");
    }

    public FullHttpResponse signuppage() throws IOException {
        return renderHtml("signup.html", "");
    }
}