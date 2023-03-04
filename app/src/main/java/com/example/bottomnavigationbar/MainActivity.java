package com.example.bottomnavigationbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.add(new MeowBottomNavigation.Model(1,R.drawable.baseline_home_48));
        bottomNav.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_category_48));
        bottomNav.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_shopping_cart_48));
        bottomNav.add(new MeowBottomNavigation.Model(4,R.drawable.baseline_search_48));
        bottomNav.add(new MeowBottomNavigation.Model(5,R.drawable.baseline_account_circle_48));

        bottomNav.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment;
                if (item.getId()==5){
                    fragment = new AccountFragment();
                }else if (item.getId()==4){
                    fragment = new SearchFragment();
                }else if (item.getId()==3){
                    fragment = new CartFragment();
                }else if (item.getId()==2){
                    fragment = new CategoryFragment();
                }else {
                    fragment = new HomeFragment();
                }

                loadFragment(fragment);
            }
        });
        bottomNav.show(1, true);
        bottomNav.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Clicked" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottomNav.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You ReClicked" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomNav.setCount(3,"10");
    }
    private  void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_container, fragment, null)
                .commit();
    }
}