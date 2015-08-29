package justapps.ud.torrentr;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class Activity_TorrMain extends ActionBarActivity implements Interface_TorrFunc {

    String currCat="all";
    RecyclerView TorrList;
    ImageView btnSearch;
    EditText txtSearch;
    FloatingActionButton FABsearch;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private Adapter_TorrNavDrawer navigationDrawerAdapter;
    //private String[] leftSliderData = {"All Latest", "Movies", "Music", "Games", "Softwares", "TV Shows", "eBooks","Settings"};

    private DataType_TorrNavDrawer[] navDrawerItems = {
            new DataType_TorrNavDrawer("All Latest",R.drawable.ic_all),
            new DataType_TorrNavDrawer("Movies",R.drawable.ic_movies),
            new DataType_TorrNavDrawer("Music",R.drawable.ic_music),
            new DataType_TorrNavDrawer("Games",R.drawable.ic_games),
            new DataType_TorrNavDrawer("Softwares",R.drawable.ic_apps),
            new DataType_TorrNavDrawer("TV Shows",R.drawable.ic_tv),
            new DataType_TorrNavDrawer("eBooks",R.drawable.ic_ebooks),
            new DataType_TorrNavDrawer("Settings",R.drawable.ic_settings)
    };

    Network_TorrListFetch torrlistreq = new Network_TorrListFetch(Activity_TorrMain.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torrmain);
        nitView();

    }



    private void nitView() {

        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        navigationDrawerAdapter= new Adapter_TorrNavDrawer(Activity_TorrMain.this,navDrawerItems);

        //navigationDrawerAdapter = new ArrayAdapter<String>(Activity_TorrMain.this, android.R.layout.simple_list_item_1, leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);


        if (toolbar != null) {
            toolbar.setTitle("Torrentr");
            setSupportActionBar(toolbar);
        }
        //TorrList = (ListView) this.findViewById(R.id.TorrList);
        TorrList = (RecyclerView) this.findViewById(R.id.TorrList);
        FABsearch = (FloatingActionButton) findViewById(R.id.fabsearch);
        FABsearch.attachToRecyclerView(TorrList);

        torrlistreq.delegate = this;

        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=all", "all");


        addListenerOnButton();
        initDrawer();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refreshing data on server
                torrlistreq.delegate = Activity_TorrMain.this;
                torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat="+currCat,currCat);
            }
        });


    }

    private void initDrawer() {
        if(drawerLayout != null) {

            drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);

                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                }
            };
            drawerLayout.setDrawerListener(drawerToggle);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("Portrait ", "PPPPPPPPPPPPPPPPP");
            setContentView(R.layout.activity_torrmain);
            nitView();

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d("L", "LLLLLLL");

            //isDrawerLocked = true;
            setContentView(R.layout.activity_torrmain_landscape);
            nitView();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.torrentr_main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchMenuItem.setVisible(false);
        FABsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMenuItem.expandActionView();
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String searchQuery=s.replaceAll("\\s", "-");
                torrlistreq.delegate = Activity_TorrMain.this;
                torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?search=" + searchQuery, searchQuery);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if (!queryTextFocused) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
private void closeDrawer(){
    if(drawerLayout != null)
        drawerLayout.closeDrawers();

}
    private void addListenerOnButton() {

        leftDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        torrlistreq.delegate = Activity_TorrMain.this;
                        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=all","all");
                        closeDrawer();
                        break;
                    case 1:
                        torrlistreq.delegate = Activity_TorrMain.this;
                        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=movies","movies");
                        closeDrawer();
                        break;
                    case 2:
                        torrlistreq.delegate = Activity_TorrMain.this;
                        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=music","music");
                        closeDrawer();
                        break;
                    case 3:
                        torrlistreq.delegate = Activity_TorrMain.this;
                        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=games","games");
                        closeDrawer();

                        break;
                    case 4:
                        torrlistreq.delegate = Activity_TorrMain.this;
                        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=apps", "apps");
                        closeDrawer();
                        break;
                    case 5:
                        torrlistreq.delegate = Activity_TorrMain.this;
                        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=tv", "tv");
                        closeDrawer();
                        break;
                    case 6:
                        torrlistreq.delegate = Activity_TorrMain.this;
                        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=ebooks", "ebooks");
                        closeDrawer();
                        break;
                    default:
                        torrlistreq.delegate = Activity_TorrMain.this;
                        torrlistreq.fetch("http://torrentr-1038.appspot.com/jresp.jsp?cat=all","all");
                        closeDrawer();
                        break;

                }
               // Toast.makeText(Activity_TorrMain.this, navigationDrawerAdapter.getItem(position), Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public void TorrList(ArrayList<Model_TorrDetail> Model_TorrDetail) {
        Adapter_TorrListRecycler adapter = new Adapter_TorrListRecycler(this, Model_TorrDetail);
        TorrList.setAdapter(adapter);
        TorrList.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
         if (mSwipeRefreshLayout.isRefreshing()) {
             mSwipeRefreshLayout.setRefreshing(false);
         }

    }

    @Override
    public void TorrDetail(String[] sites, String[] contents, String[] comments, String[] links) {

    }

    @Override
    public void TorrMagLink(String maglink) {

    }

    @Override
    public void TorrCatCurr(String cat) {
    this.currCat=cat;
    }

    @Override
    public void TorrListWTags(ArrayList<Model_TorrDetail> Model_TorrDetail, ArrayList<Model_TorrTags> Model_TorrTags) {
        Adapter_TorrListRecycler adapter = new Adapter_TorrListRecycler(this, Model_TorrDetail,Model_TorrTags);
        TorrList.setAdapter(adapter);
        //TorrList.setLayoutManager(new LinearLayoutManager(this));
       // adapter.notifyDataSetChanged();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}