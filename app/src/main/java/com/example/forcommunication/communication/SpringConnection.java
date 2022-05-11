package com.example.forcommunication.communication;

import com.example.forcommunication.util.HttpUrlConnectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public class SpringConnection {

    private static final String HOST = "http://192.168.111.1:8080";

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
        try {
            String page = HOST + path;
            URL urls = new URL(page);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            StringBuilder sb = new StringBuilder();
            if (conn != null) {
                HttpUrlConnectionUtils.setGetConfig(conn);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    int nLength;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byte[] byteData = baos.toByteArray();

                    JSONObject responseJSON = new JSONObject(new String(byteData));
                    result = responseJSON.get("Message").toString();
                }
                conn.disconnect();
            }
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public JSONObject HttpConnGETBill(String path) {
        JSONObject responseJSON = new JSONObject();
        try {
            String page = HOST + path;
            URL urls = new URL(page);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            StringBuilder sb = new StringBuilder();
            if (conn != null) {
                HttpUrlConnectionUtils.setGetConfig(conn);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    int nLength;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byte[] byteData = baos.toByteArray();

                    responseJSON = new JSONObject(new String(byteData));
                }
                conn.disconnect();
            }
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseJSON;
    }

    public JSONObject HttpConnPOSTBill(String path, BillDTO billDTO) {
        JSONObject resultJSON = new JSONObject();
        try {
            String page = HOST + path;
            URL urls = new URL(page);
            HttpURLConnection conn = (HttpURLConnection) urls.openConnection();

            StringBuilder sb = new StringBuilder();
            if (conn != null) {
                HttpUrlConnectionUtils.setPostConfig(conn);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("userId", billDTO.getUserId());
                jsonObject.put("date", billDTO.getDate());
                jsonObject.put("totalFee", billDTO.getTotalFee());
                jsonObject.put("waterFee", billDTO.getWaterFee());
                jsonObject.put("waterUsage", billDTO.getWaterUsage());
                jsonObject.put("electricityFee", billDTO.getElectricityFee());
                jsonObject.put("electricityUsage", billDTO.getElectricityUsage());


                OutputStream os = conn.getOutputStream();
                os.write(jsonObject.toString().getBytes());
                os.flush();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] byteBuffer = new byte[1024];
                    int nLength;
                    while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                        baos.write(byteBuffer, 0, nLength);
                    }
                    byte[] byteData = baos.toByteArray();

                    resultJSON = new JSONObject(new String(byteData));
                }
                conn.disconnect();
            }
            System.out.println(sb.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return resultJSON;
    }


}
