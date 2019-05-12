package news.agoda.com.sample.views;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import news.agoda.com.newsconnector.models.NewsEntity;
import news.agoda.com.sample.R;
import news.agoda.com.sample.databinding.ActivityMainBinding;
import news.agoda.com.sample.databinding.ActivityMainBindingSw600dpImpl;
import news.agoda.com.sample.viewModels.VmDetailedNews;
import news.agoda.com.sample.viewModels.VmLocator;
import news.agoda.com.sample.views.DetailViewFragment;
import news.agoda.com.sample.views.IListClickListener;
import news.agoda.com.sample.views.NewsListFragment;

public class MainActivity
        extends AppCompatActivity {

    private static final String TAG = "MainActivity:";

    private static final String NEWS_LIST_FRAGMENT_TAG = "news_list_fragment";
    private static final String DETAIL_FRAGMENT_TAG = "detail_fragment";

    private ObservableBoolean showHideDetailNewsViewInTablet = new ObservableBoolean(false);

    private boolean isAppRunningInTablet ;
    private NewsListFragment newsListFragment;
    private VmDetailedNews vmDetailedNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainActivity(this);


        Fresco.initialize(this);

        isAppRunningInTablet = getResources().getBoolean(R.bool.isTablet);
        vmDetailedNews = VmLocator.getInstance().getVmDetailedNews();

        addNewsListFragment(savedInstanceState);
        setupListClickListener();
    }

    private void addNewsListFragment(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            newsListFragment = NewsListFragment.getNewInstance();
            addFragment(newsListFragment, R.id.phone_container, NEWS_LIST_FRAGMENT_TAG);
        } else {
            newsListFragment = (NewsListFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, NEWS_LIST_FRAGMENT_TAG);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, NEWS_LIST_FRAGMENT_TAG, newsListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupListClickListener() {
        newsListFragment.setupListClickListener(this::onItemClicked);
    }

    // TODO <kartik> allow back press handling for fragment as well, for now it is not needed
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            // Hide the detailed fragment view as well if running in tablet.
            showHideDetailedContainerInTablet(false);
        } else {
            finish();
        }
    }

    public ObservableBoolean getShowHideDetailNewsViewInTablet() {
        return showHideDetailNewsViewInTablet;
    }

    private void showHideDetailedContainerInTablet(boolean show) {
        showHideDetailNewsViewInTablet.set(show);
    }

    private void onItemClicked(NewsEntity newsEntity) {
        showHideDetailedContainerInTablet(true);
        checkAndAddDetailFragment();
        vmDetailedNews.updateNewsEntity(newsEntity);
    }

    private void checkAndAddDetailFragment() {
        DetailViewFragment detailViewFragment = (DetailViewFragment) getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG);
        if (detailViewFragment == null) {
            detailViewFragment = DetailViewFragment.getNewInstance();
            int resourceId = isAppRunningInTablet ? R.id.detail_container : R.id.phone_container;
            addFragment(detailViewFragment, resourceId, DETAIL_FRAGMENT_TAG);
        } else if (!isAppRunningInTablet) {
            addFragment(detailViewFragment, R.id.phone_container, DETAIL_FRAGMENT_TAG);
        }
    }

    private void addFragment(Fragment fragment, int resourceId, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(resourceId, fragment, tag)
                .addToBackStack(tag);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();
    }
}
