package br.com.soulskyye.tecnonutri.view.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.receivers.NetworkChangeReceiver;
import br.com.soulskyye.tecnonutri.util.Utils;
import br.com.soulskyye.tecnonutri.widget.NetworkMessage;

/**
 * Created by ulissescurti on 3/8/17.
 */

public class BaseActivity extends AppCompatActivity  implements MoPubInterstitial.InterstitialAdListener, MoPubView.BannerAdListener {

    public static final String SMALL_BANNER_ID = "773d7cfde2384b2b99c3740b1c2b6885";
    private static final String ACTION_CONN_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    private MoPubInterstitial mInterstitial;
    private final NetworkChangeReceiver mNetworkStatusReceiver = new NetworkChangeReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkMessage.clearMessageWithoutAnimation();

            int status = Utils.getConnectivityStatus(context);
            if (intent.getAction().equals(ACTION_CONN_CHANGE)) {
                if (status == Utils.NETWORK_STATUS_NOT_CONNECTED) {
                    new NetworkMessage(BaseActivity.this).makeNetworkNotification().show();
                } else {
                    NetworkMessage.clearMessage();
                }
            }

        }
    };

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

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mNetworkStatusReceiver, new IntentFilter(ACTION_CONN_CHANGE));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mNetworkStatusReceiver);
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
