package justapps.ud.torrentr;

/**
 * Created by Usman-Durrani on 8/28/2015.
 */
public class Model_TorrTags {
    public String year;
    public String resolution;
    public String quality;
    public String vid_cdc;
    public String aud_cdc;
    public String uploader;
    //public String year;

    // Create a constructor
    public Model_TorrTags(String year, String resolution, String quality, String vid_cdc, String aud_cdc, String uploader) {
        this.year = year;
        this.resolution = resolution;
        this.quality = quality;
        this.vid_cdc = vid_cdc;
        this.aud_cdc = aud_cdc;
        this.uploader = uploader;
        //  this.year = year;


    }
}
