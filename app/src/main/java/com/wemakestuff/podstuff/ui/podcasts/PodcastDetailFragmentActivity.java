package com.wemakestuff.podstuff.ui.podcasts;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.github.frankiesardo.icepick.bundle.Bundles;
import com.viewpagerindicator.TitlePageIndicator;
import com.wemakestuff.podstuff.R;
import com.wemakestuff.podstuff.ui.base.BaseFragmentActivity;
import com.wemakestuff.podstuff.ui.widget.adapter.PodcastDetailPagerAdapter;
import com.wemakestuff.podstuff.ui.widget.adapter.PodcastListPagerAdapter;

import butterknife.InjectView;
import butterknife.Views;

public class PodcastDetailFragmentActivity extends BaseFragmentActivity {
    @InjectView(R.id.tpi_indicator)
    TitlePageIndicator indicator;
    @InjectView(R.id.vp_pages)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundles.restoreInstanceState(this, savedInstanceState);
        setContentView(R.layout.view_pager);
        Views.inject(this);
        pager.setAdapter(new PodcastDetailPagerAdapter(this, getSupportFragmentManager()));
        indicator.setViewPager(pager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundles.saveInstanceState(this, outState);
    }
}