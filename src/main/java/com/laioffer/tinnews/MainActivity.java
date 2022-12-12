package com.laioffer.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laioffer.tinnews.databinding.ActivityMainBinding;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private NavController navController;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = binding.navView;
        // controller, host

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        // we got the controller from the middle white part then we give to navView so we can change
        // material on the white part by using navigation
        // click on tab on BottomNavView can navigate
        NavigationUI.setupWithNavController(navView, navController);
        // can display label on action bar
        NavigationUI.setupActionBarWithNavController(this, navController);

        NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);
        // Call<NewsResponse>
        api.getTopHeadlines("us").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().toString());
                } else {
                    Log.d(TAG, response.toString());
                }
            }
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        // can click back
        return navController.navigateUp();
    }

}