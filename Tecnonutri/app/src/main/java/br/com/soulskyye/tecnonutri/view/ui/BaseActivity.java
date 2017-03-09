package br.com.soulskyye.tecnonutri.view.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;

import br.com.soulskyye.tecnonutri.R;

/**
 * Created by ulissescurti on 3/8/17.
 */

public class BaseActivity extends AppCompatActivity  implements MoPubInterstitial.InterstitialAdListener, MoPubView.BannerAdListener {

    public static final String SMALL_BANNER_ID = "773d7cfde2384b2b99c3740b1c2b6885";

    private MoPubInterstitial mInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if(mInterstitial != null){
            mInterstitial.destroy();
        }
        super.onDestroy();
    }

    public void showInterstitialAd(){
        mInterstitial = new MoPubInterstitial(this, SMALL_BANNER_ID);
        mInterstitial.setInterstitialAdListener(this);
        mInterstitial.load();
    }

    private void yourAppsShowInterstitialMethod() {
        if (mInterstitial.isReady()) {
            mInterstitial.show();
        } else {

        }
    }

    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {
        yourAppsShowInterstitialMethod();
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {

    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {

    }

    @Override
    public void onBannerLoaded(MoPubView banner) {

    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

    }

    @Override
    public void onBannerClicked(MoPubView banner) {

    }

    @Override
    public void onBannerExpanded(MoPubView banner) {

    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {

    }
}
