package com.msimw.httpservice.client.retrofit.json;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Converter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by msimw on 2017/7/12.
 * @param <T>
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    /*
    * 转换方法
    */
    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String tempStr = bufferedSource.readUtf8();
        bufferedSource.close();
        try {
            if(StringUtils.isEmpty(tempStr)){
                return null;
            }
            if(tempStr.startsWith("{")
                    ||tempStr.startsWith("\"{")
                    ||tempStr.startsWith("'{")
                    ||tempStr.startsWith("[")
                    ||tempStr.startsWith("'[")
                    ||tempStr.startsWith("\"[")){

                tempStr = tempStr.replaceAll("''","null");
                tempStr = tempStr.replaceAll("\"\"","null");
            return JSONObject.parseObject(tempStr, type);
            }
            return (T) tempStr;
        } catch (JSONException e) {
            e.printStackTrace();
            return (T) tempStr;
        }

    }



}