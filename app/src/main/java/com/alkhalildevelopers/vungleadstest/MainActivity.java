package com.alkhalildevelopers.vungleadstest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.vungle.warren.AdConfig;
import com.vungle.warren.Banners;
import com.vungle.warren.InitCallback;
import com.vungle.warren.LoadAdCallback;
import com.vungle.warren.Vungle;
import com.vungle.warren.VungleBanner;
import com.vungle.warren.error.VungleException;

public class MainActivity extends AppCompatActivity {
    Button showInterstitialBtn,showBannerBtn;
    ViewGroup bannerAdContainer;
    VungleBanner vungleBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showInterstitialBtn = findViewById(R.id.showInterstitialBtn);
        showBannerBtn = findViewById(R.id.showBannerBtn);

        bannerAdContainer = (LinearLayout) findViewById(R.id.bannerAdContainer);

        Vungle.init("5e773259af441d0001b7e2b7", getApplicationContext(), new InitCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Vungle SDK initialized successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(VungleException exception) {
                Toast.makeText(MainActivity.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAutoCacheAdAvailable(String placementId) {

            }
        });

        showInterstitialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vungle.loadAd("INTERSTITIAL-2458719", new LoadAdCallback() {
                    @Override
                    public void onAdLoad(String id) {
                        if (Vungle.canPlayAd("INTERSTITIAL-2458719")){
                            Vungle.playAd("INTERSTITIAL-2458719",null,null);
                        }
                    }

                    @Override
                    public void onError(String id, VungleException exception) {
                        Toast.makeText(MainActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        showBannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Banners.loadBanner("BANNER-3147636", AdConfig.AdSize.BANNER, new LoadAdCallback() {
                    @Override
                    public void onAdLoad(String id) {
                        if (Banners.canPlayAd("BANNER-3147636", AdConfig.AdSize.BANNER)){
                            vungleBanner = Banners.getBanner("BANNER-3147636", AdConfig.AdSize.BANNER,null);
                            bannerAdContainer.removeAllViews();
                            bannerAdContainer.addView(vungleBanner);
                        }
                    }

                    @Override
                    public void onError(String id, VungleException exception) {
                        Toast.makeText(MainActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    protected void onDestroy() {
        vungleBanner.destroyAd();
        super.onDestroy();
    }
}
