package com.vn.fa.base.data.net.request.retrofit;

import com.vn.fa.base.data.converter.FaConverterFactory;
import com.vn.fa.base.data.net.request.JsonToModelMapper;
import com.vn.fa.base.data.net.request.RequestType;
import com.vn.fa.base.data.net.request.RestEndPoints;
import com.vn.fa.net.ApiService;
import com.vn.fa.net.adapter.Request;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.http.FieldMap;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by leobui on 10/27/2017.
 */

public class RetrofitAdapterFactory extends Request.Factory implements RestEndPoints {
    private RestEndPoints restEndPoints;
    public void init(){
        restEndPoints = new ApiService.Builder()
                .baseUrl(baseUrl)
                .logging(isLogging)
                .converterFactory(FaConverterFactory.create())
                .build()
                .create(RestEndPoints.class);
    }
    @Override
    public Observable<Object> callApi(RequestType type, String path, Map<String, String> params, @HeaderMap Map<String, String> headers, final Type objType) {
        if (params == null) params = new HashMap<>();
        if (headers == null) headers = new HashMap<>();
        if (type == RequestType.GET){
            return callGetApi(path, params, headers, objType);
        }
        if (type == RequestType.POST_WITHOUT_FORM_ENCODED){
            return callPostApi(path, params, headers, objType);
        }
        if (type == RequestType.POST || type == RequestType.POST_WITH_FORM_ENCODED){
            return callPostApiWithFormUrlEncoded(path, params, headers, objType);
        }
        return null;
    }

    @Override
    public  Observable<Object> callGetApi(@Path("path") String path, @QueryMap Map<String, String> params, @HeaderMap Map<String, String> headers, final Type objType) {
        return restEndPoints.callGetApi(path, params, headers, objType).map(new Function<Object, Object>() {

            @Override
            public Object apply(Object o) {
                return JsonToModelMapper.transform(o, objType);
            }
        });
    }

    @Override
    public  Observable<Object> callPostApi(@Path("path") String path, @FieldMap Map<String, String> params, @HeaderMap Map<String, String> headers, final Type objType) {
        return restEndPoints.callPostApi(path, params, headers, objType).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                return JsonToModelMapper.transform(o, objType);
            }
        });
    }

    @Override
    public  Observable<Object> callPostApiWithFormUrlEncoded(@Path("path") String path, @FieldMap Map<String, String> params, @HeaderMap Map<String, String> headers, final Type objType) {
        return restEndPoints.callPostApiWithFormUrlEncoded(path, params, headers, objType).map(new Function<Object, Object>() {
            @Override
            public Object apply(Object o) {
                return JsonToModelMapper.transform(o, objType);
            }
        });
    }
}
