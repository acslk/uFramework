package controllers;

import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import u.http.Responses;
import uExt.routing.*;

public class MyController extends Controller {

    public FullHttpResponse homeHandler(HttpRequest request, HttpContent content) {

        return Responses.ok("home handler");

    }

}