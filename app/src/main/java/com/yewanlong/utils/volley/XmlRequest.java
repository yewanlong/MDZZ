package com.yewanlong.utils.volley;


import com.alibaba.fastjson.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.yewanlong.utils.volley.BaseRequest;
import com.yewanlong.utils.volley.RequestListener;

import java.io.UnsupportedEncodingException;

/**
 * 发送返回json字符串的请求
 *
 * @author YWL
 */
public class XmlRequest extends BaseRequest<String> {

    public XmlRequest(int method, String url, RequestListener<String> listener) {
        super(method, url, listener);
    }

    public XmlRequest(int method, String url, JSONObject jsonRequest, RequestListener<String> listener) {
        super(method, url, jsonRequest, listener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

}
