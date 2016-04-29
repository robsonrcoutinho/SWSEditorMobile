package br.com.ifba.swseditormobile.util;




import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Robson on 19/01/2016.
 */
public class HTMLParser {
    private static final String TAG = HTMLParser.class.getSimpleName();
    private static String response;
    private static String service;
    private static String address;
    private static  String operation;




    private static  String html = "<html>"
                                            +"<head>"
                                            +"<title>SWS Editor Mobile Editor</title>"
                                            +"</head>"
                                            +"<body>"
                                            +"<h1>SWS Editor Mobile Editor</h1>";

    private static String meioHtml =  "<h2>Exemplo de uso:</h2>"
                                     +"<p>O Address:</p> ";


    private static final String htmlFecha = "</body>"
                                            +"</html>";


    public static String  montaHTML(){
        html = html.concat(service);
        html = html.concat(meioHtml);
        html = html.concat(address);
        html = html.concat(operation);
        html = html.concat(htmlFecha);

        Log.d("HTML:",html);
        return html;
    }

    public void recebeService(String tag){
       this.service = tag;


    }

    public void recebeAddress(String tag){
       this.address = tag;

    }

    public void recebeOperation(String tag){
        this.operation = tag;

    }
}