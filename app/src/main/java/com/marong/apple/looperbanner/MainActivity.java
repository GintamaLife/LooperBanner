package com.marong.apple.looperbanner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AutoLoopViewPager bannerViewPager;
    private CirclePageIndicator circlePagerIndicator;
    private ArrayList<Integer> imageIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initAutoLoopViewPager();
        initCirclePageIndicator();
    }

    private void initCirclePageIndicator() {
        circlePagerIndicator = (CirclePageIndicator) findViewById(R.id.circlePagerIndicator);
        circlePagerIndicator.setViewPager(bannerViewPager);
    }

    private void initAutoLoopViewPager() {
        bannerViewPager = (AutoLoopViewPager) findViewById(R.id.bannerViewPager);
        // viewpager中边界的view不销毁,防止在轮播时闪屏
        bannerViewPager.setBoundaryCaching(true);
        // 是否自动滚动,默认true
        bannerViewPager.setCycle(true);
        // 设置滚动方向,默认向右
        bannerViewPager.setDirection(AutoLoopViewPager.RIGHT);
        // 当滚动到最后一个时,是否添加动画
        bannerViewPager.setBorderAnimation(true);
        // 滚动一个item需要的时间()
        bannerViewPager.setAutoScrollDurationFactor(10);
        // 设置滚动间隔时间 默认1500毫秒
        bannerViewPager.setInterval(3000);

        BannerAdapter bannerAdapter = new BannerAdapter(this, imageIds);
        bannerViewPager.setAdapter(bannerAdapter);
//        bannerViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


        bannerViewPager.startAutoScroll();
    }

    private void initData() {
        imageIds = new ArrayList<>();
        imageIds.add(R.drawable.guide01);
        imageIds.add(R.drawable.guide02);
        imageIds.add(R.drawable.guide03);
        imageIds.add(R.drawable.guide04);
    }


    /***********************test***********************/
    private static class MyPagerAdapter extends FragmentPagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DummyFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }
    }

    public static class DummyFragment extends Fragment {

        private static final String ARGS_POSITION = "position";
        private static final int[] COLORS = new int[] { 0xFF33B5E5, 0xFFAA66CC, 0xFF99CC00, 0xFFFFBB33, 0xFFFF4444 };

        @NonNull
        public static DummyFragment newInstance(int position) {
            final DummyFragment f = new DummyFragment();
            Bundle args = new Bundle();
            args.putInt(ARGS_POSITION, position);
            f.setArguments(args);
            return f;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            int pos = getArguments().getInt(ARGS_POSITION);
            final View view = inflater.inflate(R.layout.fragment_dummy, container, false);
            view.setBackgroundColor(COLORS[pos % 5]);
            final TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(String.valueOf(pos));
            return view;
        }
    }
}
