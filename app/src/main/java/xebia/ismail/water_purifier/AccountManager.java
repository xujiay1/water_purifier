package xebia.ismail.water_purifier;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by xujiayi on 17/4/19.
 */

public class AccountManager {

    private boolean logined;
    private String userName;
    private double balance;
    private ArrayList<Integer> cart;

    private static AccountManager accountManager=null;

    public static AccountManager getInstance()
    {
        if(accountManager==null)
            accountManager=new AccountManager();

        return accountManager;
    }

    public String getUserName()
    {
        return userName;
    }

    public double getBalance() {return balance;}

    public boolean isLogined()
    {
        return logined;
    }

    public void signout()
    {
        logined=false;
        userName="";
        balance=0;
        cart.clear();
    }

    public boolean login(String username, String passwd)
    {
        userName=username;
        logined=true;
        balance=10000;
        cart=new ArrayList<>();
        return true;
    }

    public boolean addToCart(int pid)
    {
        if(logined) {
            cart.add(pid);
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Integer> getCart()
    {
        return cart;
    }

    public boolean purchase(ArrayList<Integer> pids, double price) {
        if (price <= balance) {
            balance -= price;
            for (int i = 0; i < pids.size(); i++) {
                cart.remove(pids.get(i));
            }
            return true;
        } else {
            return false;
        }
    }

}
