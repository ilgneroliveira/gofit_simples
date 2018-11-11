package saude.funcional.atividade.exercicio.gofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saude.funcional.atividade.exercicio.gofit.Model.AuthenticateUser;
import saude.funcional.atividade.exercicio.gofit.Service.RetrofitService;
import saude.funcional.atividade.exercicio.gofit.Service.ServiceGenerator;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    Toolbar toolbar;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref =  getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        userId = sharedPref.getString("id", null);

        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        testLifestyleProfile();

        setupTabIcons();

        tabEvents();
    }

    private void testLifestyleProfile() {
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        AuthenticateUser authenticate_user = new AuthenticateUser();
        authenticate_user.setId(userId);

        service.testLifestyleProfile(authenticate_user).enqueue(new Callback<AuthenticateUser>() {
            @Override
            public void onResponse(Call<AuthenticateUser> call, Response<AuthenticateUser> response) {

                if (response.isSuccessful()) {
                    if (!response.body().getIs_valid()) {
                        Intent intent = new Intent(HomeActivity.this, LifeStylePartialActivity.class);
                        startActivity(intent);
                    }
                } else {
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                    Log.i("Error", "post submitted to API." + response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<AuthenticateUser> call, Throwable t) {
                Log.i("Error", "post submitted to API." + call.toString());
            }
        });
    }

    private void tabEvents() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Inicio");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.home, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Buscar");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.search, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Mais");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.menu, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StartFragment(), "Inicio");
        adapter.addFragment(new SearchFragment(), "Buscar");
        adapter.addFragment(new MenuFragment(), "Mais");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
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

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
