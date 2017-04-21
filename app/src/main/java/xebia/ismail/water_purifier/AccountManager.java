package xebia.ismail.water_purifier;

import android.util.Log;

import java.util.ArrayList;

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

    public boolean purchase(ArrayList<Integer> pids, double price)
    {
        if(price<=balance){
            balance-=price;
            for(int i=0;i<pids.size();i++) {
                cart.remove(pids.get(i));
            }
            return true;
        }
        else
        {
            return false;
        }
    }

}
