package mobile.skripsi.pawsandclaws.helper;

import java.util.ArrayList;

/**
 * Header Info
 * Created by @lukmanadelt on 12/2/2017.
 */

public class HeaderInfo {
    private String name;
    private ArrayList<DetailInfo> productList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DetailInfo> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<DetailInfo> productList) {
        this.productList = productList;
    }
}
