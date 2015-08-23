package justapps.ud.torrentr;

import java.util.ArrayList;

/**
 * Created by Usman on 7/9/2014.
 */

public interface Interface_TorrentFunctions {
    void resultTitle(ArrayList<Model_TorrentDetail> Model_TorrentDetail);

    void TorrDetail(String[] sites, String[] contents, String[] comments, String[] links);
    void TorrMagLink(String maglink);
}
