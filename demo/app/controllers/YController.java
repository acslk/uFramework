package controllers;

import io.netty.handler.codec.http.FullHttpResponse;
import u.http.Responses;
import uExt.routing.Controller;

public class YController extends Controller {

    public FullHttpResponse printClientById(int id){
        System.out.println("clientId is: YYY" + id);
        return Responses.ok("clientId is: YYY" + id);
    }

}
