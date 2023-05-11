package br.vet.certvet.helpers;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Https {

    public static String get(String uri) throws IOException {
        return Https.get(uri, new HashMap<>());
    }

    public static String get(String uri, Map<String, String> headers) throws IOException {
        final String method = "GET";
        final HttpsURLConnection connection = Https.factoryConnection(uri, headers);

        connection.setRequestMethod(method);

        return Https.handleConnection(connection);
    }

    private static HttpsURLConnection factoryConnection(String uri, Map<String, String> headers) throws IOException {
        URL url = new URL(uri);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setConnectTimeout(30000);

        headers.forEach(connection::setRequestProperty);

        return connection;
    }

    private static String handleConnection(HttpsURLConnection connection) throws IOException {
        final int status = connection.getResponseCode();

        String response;
        Reader streamReader;

        if (status > 299)
            streamReader = new InputStreamReader(connection.getErrorStream());
        else
            streamReader = new InputStreamReader(connection.getInputStream());

        String inputLine;
        StringBuilder content = new StringBuilder();

        try (BufferedReader in = new BufferedReader(streamReader)) {
            while ((inputLine = in.readLine()) != null)
                content.append(inputLine);
        }

        response = content.toString();
        connection.disconnect();

        if (status > 299)
            throw new ConnectException(response);

        return response;
    }
}
