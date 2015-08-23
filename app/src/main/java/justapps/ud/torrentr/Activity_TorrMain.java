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

    //ListView TorrList;
    RecyclerView TorrList;
    Network_TorrListFetch_OLD gettorrlist;
    ImageView btnSearch;
    EditText txtSearch;
    FloatingActionButton FABsearch;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] leftSliderData = {"All Latest", "Movies", "Music", "Games", "Softwares", "TV Shows", "Settings"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torrmain);
        nitView();


        if (toolbar != null) {
            toolbar.setTitle("Torrentr");
            setSupportActionBar(toolbar);
        }
        //TorrList = (ListView) this.findViewById(R.id.TorrList);
        TorrList = (RecyclerView) this.findViewById(R.id.TorrList);
        FABsearch = (FloatingActionButton) findViewById(R.id.fabsearch);
        FABsearch.attachToRecyclerView(TorrList);

        Network_TorrListFetch torrlistreq = new Network_TorrListFetch(Activity_TorrMain.this);
        torrlistreq.delegate = this;
        torrlistreq.fetch();


        gettorrlist = new Network_TorrListFetch_OLD(this, "http://torrentz.eu/verified");
        gettorrlist.delegate = this;
        //gettorrlist.execute();


        addListenerOnButton();
        initDrawer();
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refreshing data on server
                gettorrlist = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/verified");
                gettorrlist.delegate = Activity_TorrMain.this;
                gettorrlist.execute();
            }
        });


    }


    private void nitView() {

        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationDrawerAdapter = new ArrayAdapter<String>(Activity_TorrMain.this, android.R.layout.simple_list_item_1, leftSliderData);
        leftDrawerList.setAdapter(navigationDrawerAdapter);

    }

    private void initDrawer() {

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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
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
                Network_TorrListFetch_OLD searchTorr = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/search?f=" + s.replaceAll("\\s", "+"));
                searchTorr.delegate = Activity_TorrMain.this;
                searchTorr.execute();
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

    private void addListenerOnButton() {

        leftDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        gettorrlist = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/verified");
                        gettorrlist.delegate = Activity_TorrMain.this;
                        gettorrlist.execute();
                        drawerLayout.closeDrawers();
                        break;
                    case 1:
                        gettorrlist = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/verified?f=movies");
                        gettorrlist.delegate = Activity_TorrMain.this;
                        gettorrlist.execute();
                        drawerLayout.closeDrawers();
                        break;
                    case 2:
                        gettorrlist = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/verified?f=music");
                        gettorrlist.delegate = Activity_TorrMain.this;
                        gettorrlist.execute();
                        drawerLayout.closeDrawers();
                        break;
                    case 3:
                        gettorrlist = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/verified?f=games");
                        gettorrlist.delegate = Activity_TorrMain.this;
                        gettorrlist.execute();
                        break;
                    case 4:
                        gettorrlist = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/verified?f=apps");
                        gettorrlist.delegate = Activity_TorrMain.this;
                        gettorrlist.execute();
                        drawerLayout.closeDrawers();
                        break;
                    case 5:
                        gettorrlist = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/verified?f=tv");
                        gettorrlist.delegate = Activity_TorrMain.this;
                        gettorrlist.execute();
                        drawerLayout.closeDrawers();
                        break;
                    default:
                        gettorrlist = new Network_TorrListFetch_OLD(Activity_TorrMain.this, "http://torrentz.eu/verified");
                        gettorrlist.delegate = Activity_TorrMain.this;
                        gettorrlist.execute();
                        drawerLayout.closeDrawers();
                        break;

                }
                Toast.makeText(Activity_TorrMain.this, navigationDrawerAdapter.getItem(position), Toast.LENGTH_LONG).show();

            }
        });
       /* TorrList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent TorrDetail = new Intent(Activity_TorrMain.this, Activity_TorrDetails.class);
                TorrDetail.putExtra("torrLink", adapter.getItem(position));
                startActivity(TorrDetail);
               // Activity_TorrMain.this.overridePendingTransition(R.anim.slide_in_right,
                        //R.anim.slide_out_left);


            }
        });*/
    }


    @Override
    public void resultTitle(ArrayList<Model_TorrDetail> Model_TorrDetail) {
        Adapter_TorrListRecycler adapter = new Adapter_TorrListRecycler(this, Model_TorrDetail);
        TorrList.setAdapter(adapter);
        TorrList.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
        // if (mSwipeRefreshLayout.isRefreshing()) {
        //     mSwipeRefreshLayout.setRefreshing(false);
        // }

    }

    @Override
    public void TorrDetail(String[] sites, String[] contents, String[] comments, String[] links) {

    }

    @Override
    public void TorrMagLink(String maglink) {

    }


}