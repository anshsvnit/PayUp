
package com.example.mukul.workingtabs;

       import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
       import android.support.v4.app.FragmentActivity;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
       import android.content.Intent;
       import android.view.View;
       import android.widget.Button;
       import android.support.v4.app.FragmentTransaction;
       import android.view.LayoutInflater;
       import android.view.ViewGroup;
       import android.widget.ArrayAdapter;
       import android.widget.Spinner;
       import android.view.View.OnClickListener;
       import android.support.v4.app.DialogFragment;

       import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button floatButton;
    private DialogFragment hellofrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        floatButton = (Button) findViewById(R.id.fab);

        OnClickListener listener = new OnClickListener() {

            public void onClick(View v) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                hellofrag = new HelloFragment();
                hellofrag.show(fragmentManager, "hello_fragment_layout");

            }
        };

        floatButton.setOnClickListener(listener);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "RECENTS");
        adapter.addFragment(new TwoFragment(), "DEBT");
        adapter.addFragment(new ThreeFragment(), "OWED");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}