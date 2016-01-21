package com.globant.movies.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.globant.movies.R;
import com.globant.movies.databinding.ActivityMainBinding;
import com.globant.movies.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(position ->
                runOnUiThread(() ->
                        binding.recyclerView.getAdapter().notifyItemRangeInserted(position.position,
                                position.size)));
        binding.setViewModel(mainViewModel);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(new GridAdapter());

        RecyclerSimpleScrollListener scrollListener = new RecyclerSimpleScrollListener();
        binding.recyclerView.addOnScrollListener(scrollListener);
        scrollListener.getLastVisibleObservable()
                .subscribe(lastVisible -> MainActivity.this.mainViewModel.getNextPage());

        mainViewModel.getFirstPage();
    }
}
