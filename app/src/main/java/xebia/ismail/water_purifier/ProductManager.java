package xebia.ismail.water_purifier;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xujiayi on 17/4/20.
 */

public class ProductManager {

    public static ProductManager productManager=null;
    private HashMap<Integer, Product> listProducts;

    String[] brand = {
            "海尔",
            "海尔",
            "海尔",
            "海尔",
            "海尔施特劳斯",
            "海尔",
    } ;

    String[] type = {
            "前置过滤器 HP05 管道过滤 大流量安全稳压",
            "反渗透机 HRO5009-5 双出水 进口RO膜",
            "反渗透机 HRO5066-4A 箱式 微废水 节水型 大出水",
            "沐浴净化器 HS-01 美肤净水器 婴儿洗浴",
            "MAZE智能 V5HR 酒红 高端时尚，智能控温，婴儿水配方",
            "中央净水机 HU601-500 前置 超滤 不锈钢机身"
    };

    int[] product_lifes = {
            699,
            1869,
            1549,
            199,
            4999,
            899,
    } ;
    String[] principles = {
            "优质阀头,大流量,水压可视，高精度不锈滤网，安全稳压更可靠，70年寿命",
            "五级过滤可直饮，陶氏RO膜，双膜双水质出水，便捷换芯，包安装",
            "节能微废水，卡接滤芯便捷更换，双水质出水，集成水路水电分离防电防漏水，大出水量",
            "复合净水，除重金属、细菌，有效除氯等对肌肤有害成分，保证沐浴水的洁净，安装方便",
            "MAZE-V技术净水直饮，智能调温、即热即饮，滤芯提醒，便捷换芯，定量出水，双重童锁，高端选择",
            "不锈钢机身经久耐用，内压超滤出水质稳定，通水量大，安全冲洗寿命长",
    } ;

    int[] gridViewImageId = {
            R.drawable.hp05,
            R.drawable.hro5009,
            R.drawable.hro5066,
            R.drawable.hs,
            R.drawable.v5hr,
            R.drawable.hu601500,
    };
    int[] prices = {
            699,
            1488,
            1999,
            199,
            4999,
            899,
    };

    String [][] property= new String[][]{
            {"1"},
            {"3","6","8"},
            {"3","6","8"},
            {"4","7","9"},
            {"3","5","8"},
            {"2","6","8","9"}
    };

    public ProductManager()
    {
        for(int i=0;i<brand.length;i++)
        {
            Product product=new Product(i,brand[i],type[i],principles[i],prices[i],
                    product_lifes[i], property[i], gridViewImageId[i]);
            listProducts.put(i,product);
        }
    }

    public ProductManager getInstance()
    {
        if(productManager==null) {
            productManager = new ProductManager();
        }

        return productManager;
    }

    public Product getProduct(int pid)
    {
        return listProducts.get(pid);
    }
}
