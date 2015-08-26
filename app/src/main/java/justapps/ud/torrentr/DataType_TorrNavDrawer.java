package justapps.ud.torrentr;

/**
 * Created by Usman-Durrani on 8/24/2015.
 */
public class DataType_TorrNavDrawer {
    private final String category;
    private final int cat_icon;

    public DataType_TorrNavDrawer(String category, int cat_icon){
        this.category=category;
        this.cat_icon=cat_icon;
    }

    public String categoryName(){return category;}
    public int categoryIcon(){return cat_icon;}
}
