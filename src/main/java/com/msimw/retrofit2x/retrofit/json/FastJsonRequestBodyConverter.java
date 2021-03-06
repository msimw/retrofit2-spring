package com.msimw.retrofit2x.retrofit.json;

import com.alibaba.fastjson.JSON;
import com.msimw.retrofit2x.retrofit.Converter;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;

/**
 * Created by msimw on 2017/7/12.
 * @param <T>
 */
public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) throws IOException {
        return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
    }
}