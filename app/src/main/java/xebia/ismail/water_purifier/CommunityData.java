package xebia.ismail.water_purifier;

/**
 * Created by Hui on 4/5/2017.
 */

public class CommunityData {
   private String location = "";
    private int tds = 0;
    private double cl = 0;

    public CommunityData(String location, int tds, double cl) {
        this.location = location;
        this.tds = tds;
        this.cl = cl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTds() {
        return tds;
    }

    public void setTds(int tds) {
        this.tds = tds;
    }

    public double getCl() {
        return cl;
    }

    public void setCl(double cl) {
        this.cl = cl;
    }
}
