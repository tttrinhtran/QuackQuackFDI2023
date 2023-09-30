package com.example.geocare.Scan;
import android.animation.ValueAnimator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.geocare.Home.HomeActivity;
import com.example.geocare.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ScanResultActivity extends AppCompatActivity {


    final ImageView largeImageView = findViewById(R.id.ScanResultScreen_product_image);
    final View bottomSheet = findViewById(R.id.homeActivitySheet);
    ImageView back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        back_button = findViewById(R.id.ScanResultScreen_back_button);

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.homeActivitySheet));
        //initialize height
        bottomSheetBehavior.setPeekHeight(550);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        // Set the initial image height
        final int initialImageHeight = getResources().getDimensionPixelSize(R.dimen.initial_image_height);
        largeImageView.getLayoutParams().height = initialImageHeight;
        largeImageView.requestLayout();

        // Add a BottomSheetCallback
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        // Adjust image height when BottomSheet is expanded
                        final int finalImageHeight = getResources().getDimensionPixelSize(R.dimen.final_image_height);
                        ValueAnimator expandAnimator = ValueAnimator.ofInt(initialImageHeight, finalImageHeight);
                        expandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int value = (int) valueAnimator.getAnimatedValue();
                                largeImageView.getLayoutParams().height = value;
                                largeImageView.requestLayout();
                            }
                        });
                        expandAnimator.setDuration(300); // Set the animation duration
                        expandAnimator.start();
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_HIDDEN:
                        // Adjust image height when BottomSheet is collapsed or hidden
                        ValueAnimator collapseAnimator = ValueAnimator.ofInt(largeImageView.getHeight(), initialImageHeight);
                        collapseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int value = (int) valueAnimator.getAnimatedValue();
                                largeImageView.getLayoutParams().height = value;
                                largeImageView.requestLayout();
                            }
                        });
                        collapseAnimator.setDuration(300); // Set the animation duration
                        collapseAnimator.start();
                        break;
                    // Handle other states as needed
                    default:
                        break;
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                // Handle sliding animation if needed
            }
        });



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScanResultActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}