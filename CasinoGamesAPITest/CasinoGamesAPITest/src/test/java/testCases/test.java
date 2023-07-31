package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHost;
import com.mashape.unirest.http.options.Options;
import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Scanner;

public class test {

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public static void main() throws Exception {
//        String proxyHost = "YOUR_PROXY_HOST";
//        int proxyPort = 8080;
//        String urlString = "http://www.example.com/";
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
//        URL url = new URL(urlString);
//        URLConnection connection = url.openConnection(proxy);
//        // use the connection to read or write data
//    }



//    public static void main(String[] args) throws Exception {
//
//        String proxyHost = System.getProperty("http.proxyHost");
//        String proxyPort = System.getProperty("http.proxyPort");
//
//        if (proxyHost != null && proxyPort != null) {
//            // Use the proxy configuration
//            System.out.println("Using proxy server: " + proxyHost + ":" + proxyPort);
//            HttpHost proxy = new HttpHost(proxyHost, Integer.parseInt(proxyPort));
//            Unirest.setProxy(proxy);
//
//        } else {
//            // No proxy configuration found
//            System.out.println("No proxy configuration found");
//        }
//
//        // Make a request using Unirest
//        try {
//            HttpHost proxy = new HttpHost("195.191.187.226", 8080);
//            Unirest.setProxy(proxy);
//            String response = Unirest.get("http://httpbin.org/get").asString().getBody();
//            System.out.println(response);
//        } catch (UnirestException e) {
//            System.err.println("Error making request: " + e.getMessage());
//        }
//
//
////        List<String> proxyList = new ArrayList<>(); // list of proxy IP addresses
////        proxyList.add("192.168.1.1");
////        proxyList.add("192.168.1.2");
////        proxyList.add("192.168.1.3");
////        proxyList.add("192.168.1.4");
////        proxyList.add("192.168.1.5");
////        proxyList.add("192.168.1.6");
////        proxyList.add("192.168.1.7");
////        proxyList.add("192.168.1.8");
////        proxyList.add("192.168.1.9");
////        proxyList.add("192.168.1.10");
////
//        Enumeration k = NetworkInterface.getNetworkInterfaces();
//        while (k.hasMoreElements()) {
//            NetworkInterface n = (NetworkInterface) k.nextElement();
//            Enumeration kk = n.getInetAddresses();
//            while (kk.hasMoreElements()) {
//                InetAddress i = (InetAddress) kk.nextElement();
//                System.out.println("   IPS " + i);
//            }
//        }
////
//        String proxyHost = System.getProperty("http.proxyHost");
//        String proxyPort = System.getProperty("http.proxyPort");
//
//        if (proxyHost != null && proxyPort != null) {
//            // Use the proxy configuration
//            System.out.println("Using proxy server: " + proxyHost + ":" + proxyPort);
//        } else {
//            // No proxy configuration found
//            System.out.println("No proxy configuration found");
//        }
//        for (String proxyAddress : proxyList) {
//            Options options = new Options();
//            int count = 1;
//            try {
//                Unirest.setProxy(new HttpHost("192.168.31.1", 8080));
//                HttpResponse<String> response = Unirest.get("https://craftbet.com/assets/json/menu.json?=604")
//                        .header("content-type", "application/json")
////                        .setProxy(proxyAddress, 8080) // set the proxy address for this request
//
//                        .asString();
//
//                System.out.println("Response:  " + response.getStatus());
//
//                Enumeration e = NetworkInterface.getNetworkInterfaces();
//                while (e.hasMoreElements()) {
//                    NetworkInterface n = (NetworkInterface) e.nextElement();
//                    Enumeration ee = n.getInetAddresses();
//                    while (ee.hasMoreElements()) {
//                        InetAddress i = (InetAddress) ee.nextElement();
//                        System.out.println(count + "   IPS " + i);
//                    }
//                }
//                count++;
//            } catch (UnirestException eee) {
//                eee.printStackTrace();
//            } finally {
//                Unirest.shutdown();
//            }
//        }
//    }


//
//     try {
//        InetAddress address = InetAddress.getLocalHost();
//        String ipAddress = address.getHostAddress();
//        System.out.println("My network IP address is: " + ipAddress);
//    } catch (Exception ex) {
//        System.out.println("Could not find my network IP address");
//    }
//
//        try {
//        // Get the address of a remote host, such as www.google.com
//        InetAddress address = InetAddress.getByName("www.google.com");
//
//        // Get the string representation of the address, which includes the public IP address
//        String ipAddress = address.getHostAddress();
//        // Print the public IP address
//        System.out.println("My public IP address is: " + ipAddress);
//    } catch (Exception ee) {
//        System.out.println("Failed to get public IP address: " + ee.getMessage());
//    }

    public static void main(String[] args) throws IOException {
        try {
            URL url = new URL("https://api.ipify.org");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int statusCode = conn.getResponseCode();
            if (statusCode == 200) {
                Scanner scanner = new Scanner(conn.getInputStream(), "UTF-8");
                String ipAddress = scanner.useDelimiter("\\A").next();
                scanner.close();
                System.out.println("My public IP address is: " + ipAddress);
            } else {
                System.out.println("Could not get public IP address (HTTP status code: " + statusCode + ")");
            }
        } catch (IOException ex) {
            System.out.println("Could not get public IP address: " + ex.getMessage());
        }





        String proxyUrl = "http://username:password@us-wa.proxymesh.com:31280"; // Replace with your ProxyMesh URL
//        HttpHost proxyHost = new HttpHost(proxyUrl);
        HttpHost proxyHost = new HttpHost("103.75.196.121",80);

        Unirest.setProxy(proxyHost);

        // Make a request using Unirest
        try {
            HttpResponse<String> response = Unirest.get("https://craftbet.com/assets/json/menu.json?=604")
                        .header("content-type", "application/json")
                                .asString();

            System.out.println(response.getBody());
        } catch (UnirestException e) {
            System.err.println("Error making request: " + e.getMessage());
        }

        finally {
            Unirest.shutdown();
        }




        try {
            URL url = new URL("https://api.ipify.org");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int statusCode = conn.getResponseCode();
            if (statusCode == 200) {
                Scanner scanner = new Scanner(conn.getInputStream(), "UTF-8");
                String ipAddress = scanner.useDelimiter("\\A").next();
                scanner.close();
                System.out.println("My public IP address is: " + ipAddress);
            } else {
                System.out.println("Could not get public IP address (HTTP status code: " + statusCode + ")");
            }
        } catch (IOException ex) {
            System.out.println("Could not get public IP address: " + ex.getMessage());
        }







    }


    @Test
    public void getDuplicateGames() throws JSONException {
        try {

            int user = 1871519;
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post("https://adminwebapi.iqsoftllc.com/api/Main/ApiRequest?TimeZone=4&LanguageId=en&token=da110bf8-9a2e-4787-b1a1-312430d689dd")
                    .header("Content-Type", "application/json")
                    .body("{\r\n    \"Method\": \"SaveProductLimit\",\r\n    \"Controller\": \"Util\",\r\n    \"RequestObject\": {\r\n        \"Method\": \"SaveProductLimit\",\r\n        \"Controller\": \"Util\",\r\n        \"Id\": 239,\r\n        \"MaxLimit\": 5,\r\n        \"ProductId\": 65,\r\n        \"ObjectId\": \"" + user + "      \",\r\n        \"ObjectTypeId\": 2\r\n    },\r\n    \"Token\": \"da110bf8-9a2e-4787-b1a1-312430d689dd\"\r\n}")
                    .asString();

            System.out.println("Body: " + response.getBody());
            System.out.println();
        } catch (Exception e) {
            System.out.println("getDuplicateGamesTest has an exception" + e);
            Assert.fail();
        }
    }


    }









//        public static void proxy() throws Exception {
//            String urlString = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1280px-Image_created_with_a_mobile_phone.png";
//            String proxyHost = "192.168.31.63";
//            int proxyPort = 8080;
//            Proxy proxy = new Proxy(Proxy.Type.DIRECT, new InetSocketAddress(proxyHost, proxyPort));
//            URL url = new URL(urlString);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//            reader.close();
//        }
//    }

