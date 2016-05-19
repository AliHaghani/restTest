package me.alihaghani;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Scanner;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import sun.misc.IOUtils;


public class Main {



    static int totalCount = 0;
    static double totalBalance = 0.00;

    static DecimalFormat df = new DecimalFormat("#.##");

    private static String jsonLocation = "http://resttest.bench.co/transactions/";

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter page number:");
        int page = input.nextInt();
        getData(page);

        System.out.println("Total balance: $" + df.format(totalBalance));
        System.out.println("Total count: " + totalCount);
        System.out.println("Page number: " + page);



    }

    public static void getData(int page) throws Exception {
        final JSONObject obj = new JSONObject(readUrl(jsonLocation + Integer.toString(page) + ".json"));
        final JSONArray transactions = obj.getJSONArray("transactions");
        final int pageLength = transactions.length();

        for (int i = 0; i < pageLength; i++) {
            final JSONObject transaction = transactions.getJSONObject(i);
            System.out.println("Date: " + transaction.getString("Date"));
            System.out.println("Ledger: " + transaction.getString("Ledger"));
            System.out.println("Amount: $" + transaction.getDouble("Amount"));
            System.out.println("Company: " + transaction.getString("Company"));
            System.out.println("");
            totalCount++;
            totalBalance += transaction.getDouble("Amount");
        }

    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

}
