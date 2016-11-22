package controllers;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import u.http.Responses;
import uExt.routing.*;

import java.util.ArrayList;

public class MyController extends Controller {

    //GET
    public FullHttpResponse home() {
        return Responses.ok("Hello World");
    }

    public FullHttpResponse printClientById(int id){
        System.out.println("clientId is: " + id);
        return Responses.ok("clientId is: " + id);
    }


}