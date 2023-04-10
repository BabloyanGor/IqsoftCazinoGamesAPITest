package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpHost;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {
        List<String> proxyList = new ArrayList<>(); // list of proxy IP addresses
        proxyList.add("192.168.1.1");
        proxyList.add("192.168.1.2");
        proxyList.add("192.168.1.3");
        proxyList.add("192.168.1.4");
        proxyList.add("192.168.1.5");
        proxyList.add("192.168.1.6");
        proxyList.add("192.168.1.7");
        proxyList.add("192.168.1.8");
        proxyList.add("192.168.1.9");
        proxyList.add("192.168.1.10");

        Enumeration k = NetworkInterface.getNetworkInterfaces();
        while (k.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) k.nextElement();
            Enumeration kk = n.getInetAddresses();
            while (kk.hasMoreElements()) {
                InetAddress i = (InetAddress) kk.nextElement();
                System.out.println("   IPS " + i);
            }
        }

        for (String proxyAddress : proxyList) {

            int count = 1;




            try {
                Unirest.setProxy(new HttpHost(proxyAddress, 80));
                HttpResponse<String> response = Unirest.get("https://craftbet.com/assets/json/menu.json?=604")
                        .header("content-type", "application/json")
//                        .setProxy(proxyAddress, 8080) // set the proxy address for this request

                        .asString();

                System.out.println("Response:  " + response.getStatus());

                Enumeration e = NetworkInterface.getNetworkInterfaces();
                while (e.hasMoreElements()) {
                    NetworkInterface n = (NetworkInterface) e.nextElement();
                    Enumeration ee = n.getInetAddresses();
                    while (ee.hasMoreElements()) {
                        InetAddress i = (InetAddress) ee.nextElement();
                        System.out.println(count + "   IPS " + i);
                    }
                }
                count ++;
            } catch (UnirestException eee) {
                eee.printStackTrace();
            }
            finally {
                Unirest.shutdown();
            }
        }
    }
}
