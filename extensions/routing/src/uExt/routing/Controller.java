package uExt.routing;

import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;

public abstract class Controller {

    private HttpRequest request;
    private HttpContent content;

    public void init(){

    }

    public void beforeHandling(){

    }

    public void setRequestInfo (HttpRequest request, HttpContent content) {
        this.request = request;
        this.content = content;
    }

    protected HttpRequest getRequest() {
        return request;
    }

    protected HttpContent getContent() {
        return content;
    }

}