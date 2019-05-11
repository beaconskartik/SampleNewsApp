package news.agoda.com.sample;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

import news.agoda.com.newsconnector.models.NewsEntity;
import news.agoda.com.sample.views.DetailViewFragment;
import news.agoda.com.sample.views.IListClickListener;
import news.agoda.com.sample.views.NewsListFragment;

public class MainActivity
        extends AppCompatActivity implements IListClickListener {

    private static final String TAG = "MainActivity:";
    private boolean isAppRunningInTablet = false;
    NewsListFragment newsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        isAppRunningInTablet = getResources().getBoolean(R.bool.isTablet);
        if(savedInstanceState == null) {
            newsListFragment = new NewsListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction
                    .add(R.id.phone_container, newsListFragment, "news_list_fragment")
                    .addToBackStack("newsListFragment");
            fragmentTransaction.commit();
        } else {
            newsListFragment = (NewsListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "news_list_fragment");
        }

        setupListClickListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, "news_list_fragment", newsListFragment);
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
        newsListFragment.setupListClickListener(this);
    }

    // TODO <kartik> allow back press handling for fragment as well, for now it is not needed
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            showhideDetailedContainerInTablet(true);
        } else {
            finish();
        }
    }

    private void showhideDetailedContainerInTablet(boolean hide) {
        if (isAppRunningInTablet) {
            View view = findViewById(R.id.detail_container);
            view.setVisibility(hide ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onItemClicked(NewsEntity newsEntity) {
        String title = newsEntity.getTitle();
        String newsUrl = newsEntity.getUrl();
        String summary = newsEntity.getAbstract();
        String imageUrl = "";
        if (newsEntity.isMediaEntityPresent()) {
            imageUrl = newsEntity.getMediaEntity().get(0).getUrl();
        }

        showhideDetailedContainerInTablet(false);
        int resourceId = isAppRunningInTablet ? R.id.detail_container : R.id.phone_container;
        DetailViewFragment detailViewFragment = DetailViewFragment
                .getNewInstance(newsUrl, title, summary, imageUrl);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(resourceId, detailViewFragment, "detail_fragment")
                .addToBackStack("detail_fragment");
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Fresco.shutDown();
    }
}
