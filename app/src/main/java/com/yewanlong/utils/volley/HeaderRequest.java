package com.yewanlong.utils.volley;


import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * 发送返回header的请求，读取header
 * @author YWL
 *
 */
public class HeaderRequest extends BaseRequest<String> {

    final String headerKey;

    public HeaderRequest(int method, String url, String headerKey, RequestListener<String> listener) {
        super(method, url, listener);
        this.headerKey = headerKey;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        final String value = response.headers.get(headerKey);
        if (value != null) {
            return Response.success(value, getCacheEntry());
        } else {
            return Response.error(new VolleyError("header not found :: " + response.headers.toString()));
        }
    }

}
