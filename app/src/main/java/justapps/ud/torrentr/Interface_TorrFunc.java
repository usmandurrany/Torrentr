package justapps.ud.torrentr;

import java.util.ArrayList;

/**
 * Created by Usman on 7/9/2014.
 */

public interface Interface_TorrFunc {
    void TorrList(ArrayList<Model_TorrDetail> Model_TorrDetail);
    void TorrListWTags(ArrayList<Model_TorrDetail> Model_TorrDetail,ArrayList<Model_TorrTags> Model_TorrTags);

    void TorrDetail(String[] sites, String[] contents, String[] comments, String[] links);

    void TorrMagLink(String maglink);

    void TorrCatCurr(String cat);
}
