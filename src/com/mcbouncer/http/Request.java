package com.mcbouncer.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;

/**
 * Request container class. Contains a URL, method, parameters,
 * HTTP headers to send, etc. We don't use too much of this now,
 * but in the future, it may be useful.
 * 
 */
public class Request {
    protected String url = "";
    protected RequestType method = RequestType.GET;
    protected Map<String, String> parameters;
    protected List<Header> headers;

    public Request(Map<String, String> parameters, List<Header> headers) {
        this.parameters = parameters;
        this.headers = headers;
    }

    public Request(Map<String, String> parameters) {
        this.parameters = parameters;
        this.headers = new ArrayList<Header>();
    }

    public Request() {
        this.parameters = new HashMap<String, String>();
        this.headers = new ArrayList<Header>();
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public RequestType getMethod() {
        return method;
    }

    public void setMethod(RequestType method) {
        this.method = method;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }
}
