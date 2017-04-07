package xebia.ismail.water_purifier;

import android.media.Image;

/**
 * Created by Hui on 4/7/2017.
 */

public class product {


    String name = "";
    int [] property;
    Image icon;
    String principle;
    int price;


    double lifecyclecost;

    public product(String name, int[] property, Image icon, String principle, int price, double lifecyclecost) {
        this.name = name;
        this.property = property;
        this.icon = icon;
        this.principle = principle;
        this.price = price;
        this.lifecyclecost = lifecyclecost;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getProperty() {
        return property;
    }

    public void setProperty(int[] property) {
        this.property = property;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getLifecyclecost() {
        return lifecyclecost;
    }

    public void setLifecyclecost(double lifecyclecost) {
        this.lifecyclecost = lifecyclecost;
    }
}
