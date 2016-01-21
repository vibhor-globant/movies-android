package com.globant.movies.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.globant.movies.R;
import com.globant.movies.databinding.ActivityDetailsBinding;
import com.globant.movies.model.MoviesModel;
import com.globant.movies.model.Result;
import com.globant.movies.network.NetworkService;
import com.globant.movies.viewmodel.DetailsViewModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    public final static String EXTRA_POSITION = "com.globant.movies.details.position";
    private ActivityDetailsBinding binding;
    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        Intent intent = getIntent();
        int position = intent.getIntExtra(DetailsActivity.EXTRA_POSITION, -1);
        if (position >= 0) {
            detailsViewModel = new DetailsViewModel(position, getApplicationContext(), color -> {
                Window window = DetailsActivity.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(color);
            });
            binding.setViewModel(detailsViewModel);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
