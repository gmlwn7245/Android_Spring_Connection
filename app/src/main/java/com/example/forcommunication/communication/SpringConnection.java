package com.example.forcommunication.communication;

import com.example.forcommunication.util.HttpUrlConnectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public class SpringConnection {

    private static final String HOST = "http://10.0.2.2:8080/";

    public String HttpConnPOSTUser(String path, UserDTO userDTO) {

        HttpURLConnection conn = null;
        try {
            String page = HOST + path;
            URL urls = new URL(page);
            conn = (HttpURLConnection) urls.openConnection();

            if (conn != null) {
                HttpUrlConnectionUtils.setPostConfig(conn);
                HttpUrlConnectionUtils.setRequestBody(conn, userDTO);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Map<String, Object> response = HttpUrlConnectionUtils.getResponse(conn,
                            new TypeReference<Map<String, Object>>() {
                            }
                    );

                    return Optional.ofNullable(response)
                            .map(res -> res.get("Message"))
                            .map(Object::toString)
                            .orElse("");
                }
            }

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public String HttpConnGETUser(String path) {
        String result = "";
        HttpURLConnection conn = null;
        try {
            String page = HOST + path;
            URL urls = new URL(page);
            conn = (HttpURLConnection) urls.openConnection();

            if (conn != null) {
                HttpUrlConnectionUtils.setGetConfig(conn);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Map<String, Object> response = HttpUrlConnectionUtils.getResponse(conn,
                            new TypeReference<Map<String, Object>>() {
                            });

                    return Optional.ofNullable(response)
                            .map(res -> res.get("Message"))
                            .map(Object::toString)
                            .orElse("");
                }

            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public String HttpConnPOSTBill(String path, BillDTO billDTO) {
        HttpURLConnection conn = null;
        try {
            String page = HOST + path;
            URL urls = new URL(page);
            conn = (HttpURLConnection) urls.openConnection();

            StringBuilder sb = new StringBuilder();
            if (conn != null) {
                HttpUrlConnectionUtils.setPostConfig(conn);
                HttpUrlConnectionUtils.setRequestBody(conn, billDTO);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Map<String, Object> response = HttpUrlConnectionUtils.getResponse(conn,
                            new TypeReference<Map<String, Object>>() {
                            });

                    return Optional.ofNullable(response)
                            .map(res -> res.get("Data"))
                            .map(Object::toString)
                            .orElse("");
                }
            }

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public String HttpConnGETBill(String path) {
        HttpURLConnection conn = null;
        try {
            String page = HOST + path;
            URL urls = new URL(page);
            conn = (HttpURLConnection) urls.openConnection();

            if (conn != null) {
                HttpUrlConnectionUtils.setGetConfig(conn);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Map<String, Object> response = HttpUrlConnectionUtils.getResponse(conn,
                            new TypeReference<Map<String, Object>>() {
                            }
                    );

                    return Optional.ofNullable(response)
                            .map(res -> res.get("Data"))
                            .map(Object::toString)
                            .orElse("");
                }
            }

            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }





}