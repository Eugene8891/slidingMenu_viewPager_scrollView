package com.example.slidingmenu_viewpager_scrollview.ui;

import java.util.ArrayList;
import com.example.slidingmenu_viewpager_scrollview.R;
import com.example.slidingmenu_viewpager_scrollview.components.SlidingMenu;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private ViewPager viewPager;  
    private ArrayList<Fragment> fragmentList;  
    private TextView tv_tab_frag1;
    private TextView tv_tab_frag2;
    private ImageView iv_viewPagerCursor;
    private int screenWidth;
    private int currentPageIndex;
    private ImageView iv_menu;
    private SlidingMenu slidingMenu;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		slidingMenu = (SlidingMenu)findViewById(R.id.sliding_menu);
		iv_menu = (ImageView)findViewById(R.id.iv_menu);
		iv_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slidingMenu.toggle();
			}
		});
		initViewPagerTabs();
		InitViewPager();
	}
	
	private void initViewPagerTabs() {
		tv_tab_frag1 = (TextView)findViewById(R.id.tv_tab_frag1);
		tv_tab_frag2 = (TextView)findViewById(R.id.tv_tab_frag2);
		tv_tab_frag1.setOnClickListener(new tabListener(0));
		tv_tab_frag2.setOnClickListener(new tabListener(1));
		initViewPagerCursor();
	}
	
	private void initViewPagerCursor() {
		DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        screenWidth = dm.widthPixels;
		iv_viewPagerCursor = (ImageView)findViewById(R.id.iv_cursor);
		RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) iv_viewPagerCursor.getLayoutParams();
		linearParams.width = screenWidth/2;
		iv_viewPagerCursor.setLayoutParams(linearParams);
	}
	
	class tabListener implements OnClickListener{  
        private int index = 0;  
          
        public tabListener(int i) {  
            index = i;  
        }  
        @Override  
        public void onClick(View v) {  
        	viewPager.setCurrentItem(index);  
        }  
    }  
	
	public void InitViewPager(){  
		viewPager = (ViewPager)findViewById(R.id.viewPager);  
        fragmentList = new ArrayList<Fragment>();  
        fragmentList.add(new Fragment1());  
        fragmentList.add(new Fragment2());  
        viewPager.setAdapter(new DemoFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));  
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new DemoOnPageChangeListener());
    }  
	
	private class DemoFragmentPagerAdapter extends FragmentPagerAdapter{  
	    ArrayList<Fragment> list;  
	    public DemoFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {  
	        super(fm);  
	        this.list = list;  
	    }  
	      
	    @Override  
	    public int getCount() {  
	        return list.size();  
	    }  
	      
	    @Override  
	    public Fragment getItem(int arg0) {  
	        return list.get(arg0);  
	    }  
	      
	}  
	
	private class DemoOnPageChangeListener implements OnPageChangeListener{  
          
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
              
        }  
          
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
              
        }  
          
        @Override  
        public void onPageSelected(int arg0) {  
            Animation animation = new TranslateAnimation(currentPageIndex * screenWidth/2, arg0 * screenWidth/2,0,0);
            currentPageIndex = arg0;  
            animation.setFillAfter(true);
            animation.setDuration(200);
            iv_viewPagerCursor.startAnimation(animation);
        }  
    }  
}
