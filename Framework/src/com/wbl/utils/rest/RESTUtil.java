package com.wbl.utils.rest;

import com.wbl.utils.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by svelupula on 8/25/2015.
 */
public class RESTUtil {

    private final Configuration _configuration;
    private Logger _logger;
    private HttpUriRequest request;
    private HttpResponse response;
    private Headers header = null;//Header[] headers; //response.getAllHeaders();
    private WJsonObject json = null;
    private JSONArray jsonArray;
    //private JSONObject jsonObj;

    public RESTUtil(Configuration configuration) {
        _configuration = configuration;
        _logger = Logger.getLogger(RESTUtil.class);
       this.header = new Headers();
    }

    public WJsonObject getJson() {
        return json;
    }

    public void setJson(WJsonObject json) {
        this.json = json;
    }

    private void get(String resource, String contentType, String accept, String authorization) throws Exception {
        request = new HttpGet(_configuration.BaseURI + resource);
        if (contentType != null)
            request.setHeader("Content-Type", contentType);
        if (accept != null)
            request.setHeader("Accept", accept);
        if (authorization != null)
            request.setHeader("Authorization", authorization);
        request.addHeader("User-Agent", "USER_AGENT");
        response = HttpClientBuilder.create().build().execute(request);
        setHeader(response.getAllHeaders());
       // headers = response.getAllHeaders();
    }

    private void setHeader(Header[] mHeaders)
    {
        header.headers = mHeaders;
    }

    public void getJSONArray(String resource) throws Exception {
        get(resource, null, "application/json", null);
        String json = IOUtils.toString(response.getEntity().getContent());
        jsonArray = new JSONArray(json);
    }

    public void getJSONEntity(String resource) throws Exception {
        get(resource, null, "application/json", null);
       // json.setJsonObject(IOUtils.toString(response.getEntity().getContent()));
        String json = IOUtils.toString(response.getEntity().getContent());
        JSONObject obj = new JSONObject(json);
        new WJsonObject(obj);
       // this.jsonObj = new JSONObject(jsonObj);
    }


    public boolean isValidResponse() {
        return (response != null);
    }

    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

    public String getLocale() { return response.getLocale().toString();}


}
