package xebia.ismail.water_purifier;

/**
 * Created by xujiayi on 17/4/20.
 */

public class Product {
    private int id;
    private String brand;
    private String type;
    private String feature;
    private double price;
    private double lifecyclePrice;
    private String[] category;
    private int imageid;

    public Product(int id, String brand, String type, String feature,
                   double price, double lifecyclePrice, String [] category, int imageid)
    {
        this.id=id;
        this.brand=brand;
        this.type=type;
        this.feature=feature;
        this.price=price;
        this.lifecyclePrice=lifecyclePrice;
        this.category=category;
        this.imageid=imageid;
    }

    public int getId(){return id;}

    public String getBrand(){return brand;}

    public String getType(){return type;}

    public String getFeature(){return feature;}

    public double getPrice(){return price;}

    public double getLifecyclePrice(){return lifecyclePrice;}

    public int getImageid(){return imageid;}
}
