package justapps.ud.torrentr;

import android.content.Context;

/**
 * Created by Usman-Durrani on 8/14/2015.
 */
public class Model_TorrentDetail {
    public String title;
    public String size;
    public String date;
    public String verify;
    public String seeds;
    public String leeches;
    public String links;

    // Create a constructor
    public Model_TorrentDetail(String title, String size, String date, String verify, String seeds, String leeches, String links) {
        this.title = title;
        this.size = size;
        this.date = date;
        this.verify = verify;
        this.seeds = seeds;
        this.leeches = leeches;
        this.links = links;


    }
}
