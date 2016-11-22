package controllers;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import u.http.Responses;
import uExt.routing.*;

import java.util.ArrayList;

public class MyController extends Controller {
    private ArrayList<Integer> clientList;
    public MyController (){
        clientList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            clientList.add(i);
        }
    }

    //GET
    public FullHttpResponse homeHandler() {
        return Responses.ok(getRequest().uri());
    }

    public FullHttpResponse printClientById(int id){
        System.out.println("clientId is: " + clientList.get(id));
        return Responses.ok("clientId is: " + clientList.get(id));
    }


}