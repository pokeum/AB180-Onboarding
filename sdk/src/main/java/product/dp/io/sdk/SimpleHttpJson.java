package product.dp.io.sdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import product.dp.io.sdk.BadResponseCodeException;
import product.dp.io.sdk.GetResponseException;

public class SimpleHttpJson {

    private static final String SCHEME = "https://";
    private static final String HOST = "ab180-sdk-coding-assignment.vercel.app";
    private static final String PATH = "/api/track-event";

    private String mScheme;
    private String mHost;
    private String mPath;
    private String mData;

    /** Constructor */
    public SimpleHttpJson(String data) { this(SCHEME, HOST, PATH, data); }
    public SimpleHttpJson(String scheme, String host, String path, String data) {
        mScheme = scheme;
        mHost = host;
        mPath = path;
        mData = data;
    }

    public String getURL() {

        StringBuilder url = new StringBuilder();
        url.append(mScheme).append(mHost).append(mPath);
        return url.toString();
    }

    public String sendPost() throws Exception {
        URL url = new URL(getURL());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestMethod("POST");

        // If the timeout expires before the connection can be established,
        // a java.net.SocketTimeoutException is raised.
        //connection.setConnectTimeout(1);       // DEBUG
        connection.setConnectTimeout(10000);    // 10 sec

        connection.setUseCaches(false);         // 캐싱데이터 수신 여부
        connection.setDoOutput(true);           // 쓰기모드 지정
        connection.setDoInput(true);            // 읽기모드 지정

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(mData.getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            try {   // Success
                return getResponse(connection);
            } catch (IOException exc) {
                // 서버에 데이터를 전달했지만, getResponse() 함수에서 예외가 발생한 경우
                throw new GetResponseException(exc.toString());
            }
        } else {
            // Response Code: 500 {"error":"Internal Server Error"}
            // 서버는 약 10%의 확률로 에러를 내려주도록 작성되어 있습니다.
            throw new BadResponseCodeException("Code-" + responseCode);
        }
    }

    private String getResponse(HttpURLConnection connection) throws IOException {

        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String inputLine = null;
        StringBuilder response = new StringBuilder();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        return response.toString().trim();
    }
}
