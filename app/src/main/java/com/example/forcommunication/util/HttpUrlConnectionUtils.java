package com.example.forcommunication.util;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpUrlConnectionUtils {

    private static final int DEFAULT_TIMEOUT = 10000;

    public static void setPostConfig(HttpURLConnection connection) {
        setPostConfig(connection, DEFAULT_TIMEOUT);
    }

    public static void setPostConfig(HttpURLConnection connection, int timeout) {
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        try {
            connection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public static void setGetConfig(HttpURLConnection connection) {
        setGetConfig(connection, DEFAULT_TIMEOUT);
    }

    public static void setGetConfig(HttpURLConnection connection, int timeout) {
        connection.setConnectTimeout(timeout);
        connection.setDoInput(true);

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public static void setRequestBody(HttpURLConnection connection, Object requestBody) {
        try {
            String json = JsonUtils.writeValue(requestBody)
                    .orElse("");

            OutputStream os = connection.getOutputStream();
            os.write(json.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getResponse(HttpURLConnection connection, Class<T> clazz) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[1024];
            int length;

            InputStream is = connection.getInputStream();
            while ((length = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                baos.write(byteBuffer, 0, length);
            }

            return JsonUtils.readValue(baos.toString(), clazz)
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getResponse(HttpURLConnection connection, TypeReference<T> typeReference) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] byteBuffer = new byte[1024];
            int length;

            InputStream is = connection.getInputStream();
            while ((length = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                baos.write(byteBuffer, 0, length);
            }

            return JsonUtils.readValue(baos.toString(), typeReference)
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
