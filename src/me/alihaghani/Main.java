package me.alihaghani;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;


public class Main {

    static int totalCount;
    static double totalBalance;

    static HashMap<String, ArrayList<JSONObject>> categories = new HashMap<>();

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
        System.out.println("");
        printCategories(categories);



    }

    private static void printCategories(HashMap<String, ArrayList<JSONObject>> categories) {

        System.out.println("CATEGORIES");
        System.out.println("");
        for (Map.Entry<String, ArrayList<JSONObject>> entry : categories.entrySet()){

            totalCount = 0;
            totalBalance = 0.00;       // reset totals for category

            if (!entry.getKey().equals("")) {
                System.out.println("Category Name: " + entry.getKey().trim());
                System.out.println("");
            } else {
                System.out.println("Uncategorized");
                System.out.println("");
            }

            ArrayList<JSONObject> catArr = entry.getValue();
            for (int i = 0; i < catArr.size(); i++){
                printTransaction(catArr.get(i));
            }
            System.out.println(entry.getKey().trim() + " Total Expense: $" + df.format(totalBalance));
            System.out.println("");
            System.out.println("");
        }
    }

    public static void getData(int page) throws Exception {
        totalCount = 0;
        totalBalance = 0.00;        // reset totals


        final JSONObject obj = new JSONObject(readUrl(jsonLocation + Integer.toString(page) + ".json"));
        final JSONArray transactions = obj.getJSONArray("transactions");
        final int pageLength = transactions.length();
        HashSet<String> alreadyAdded = new HashSet<String>(); // HashSet to prevent duplicates

        for (int i = 0; i < pageLength; i++) {
            final JSONObject transaction = transactions.getJSONObject(i);
            if (!alreadyAdded.contains(transaction.toString())) { // Duplicate check
                alreadyAdded.add(transaction.toString());

                String category = transaction.getString("Ledger").trim();  // Category name

                if (!categories.containsKey(category)){
                    ArrayList catArray = new ArrayList();
                    catArray.add(transaction);
                    categories.put(category, catArray);
                } else {
                    categories.get(category).add(transaction);
                }

                printTransaction(transaction);


            } else continue; // Go to next item if a duplicate

        }
    }

    private static void printTransaction(JSONObject transaction) {


        System.out.println("Date: " + transaction.getString("Date"));
        System.out.println("Ledger: " + transaction.getString("Ledger"));
        System.out.println("Amount: $" + transaction.getDouble("Amount"));
        String company = transaction.getString("Company");

        // Hardcoding like this is something we usually try to avoid but due to the unpredictable
        // formatting of the company names, it is something we have to do which works for the given JSONs
        company = company.split(" VANCOUVER BC| CALGARY AB|ROVICTORIA BC| xxxx|-|#| 78 POSTAL",2)[0];

        System.out.println("Company: " + company);
        System.out.println(""); // Space between transactions

        totalCount++;
        totalBalance += transaction.getDouble("Amount");
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
