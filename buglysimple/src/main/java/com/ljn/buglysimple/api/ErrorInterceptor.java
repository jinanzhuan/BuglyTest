package com.ljn.buglysimple.api;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ljn.buglysimple.bean.PostOkModel;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by songyuqiang on 16/11/3.
 */
public class ErrorInterceptor implements Interceptor {
    private Gson mGson;

    public ErrorInterceptor(Gson gson) {

        mGson = gson;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response response = chain.proceed(chain.request());

        if (!response.isSuccessful()) {
            if(judgeErrorMsg(response) == null) {
                throw new EhException("网络异常");
            }
            return judgeErrorMsg(response);

        } else {
            return response;
        }
    }

    private Response judgeErrorMsg(Response response) throws IOException {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody body = clone.body();
        if(body == null) {
            return null;
        }
        MediaType mediaType = body.contentType();
        if(mediaType == null) {
            return null;
        }
        if (!isText(mediaType)) {
            return null;
        }
        //获取Response结果
        Log.e("ErrorInterceptor", response.request().url().toString());
        String content = readResponseStr(response);
        PostOkModel model = null;
        try {
            model = mGson.fromJson(content, PostOkModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(model == null) {
            return null;
        }
        if(model.getCode() != 4601) {
            if(TextUtils.isEmpty(model.getMsg())) {
                return null;
            }
            throw new EhException(model.getMsg());
        }else {
            body = ResponseBody.create(mediaType, content);
            return response.newBuilder().body(body).build();
        }

    }

    /**
     * 读取Response返回String内容
     * @param response
     * @return
     */
    private String readResponseStr(Response response) {
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        MediaType contentType = body.contentType();
        Charset charset = Charset.forName("UTF-8");
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String s = null;
        Buffer buffer = source.buffer();
        if (isPlaintext(buffer)) {
            s = buffer.clone().readString(charset);
        }
        return s;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean isText(MediaType mediaType)
    {
        if (mediaType.type() != null && mediaType.type().equals("text"))
        {
            return true;
        }
        if (mediaType.subtype() != null)
        {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }
}
