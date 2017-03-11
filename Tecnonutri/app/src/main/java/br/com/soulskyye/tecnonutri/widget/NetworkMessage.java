package br.com.soulskyye.tecnonutri.widget;

import android.app.Activity;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import br.com.soulskyye.tecnonutri.R;
import br.com.soulskyye.tecnonutri.view.ui.FeedActivity;
import br.com.soulskyye.tecnonutri.view.ui.FeedDetailsActivity;

/**
 * Created by ulissescurti on 3/11/17.
 */

public class NetworkMessage {

    public static Activity activity;
    public static FrameLayout frameLayoutContainer;
    public static LinearLayout linearLayout;
    private static boolean isShown = false;


    public NetworkMessage(Activity activity) {
        NetworkMessage.activity = activity;
    }

    public NetworkMessage makeNetworkNotification() {

        frameLayoutContainer = (FrameLayout) activity.findViewById(R.id.framelayout_container_network);
        linearLayout = new LinearLayout(activity);

        return this;
    }

    public void show() {
        if (linearLayout != null && frameLayoutContainer != null) {
            linearLayout.inflate(activity, R.layout.notification_internet_status, linearLayout);

            TextView textViewMessage = (TextView) linearLayout.findViewById(R.id
                    .text_view_message);
            textViewMessage.setText(R.string.not_connected_message);

            frameLayoutContainer.addView(linearLayout);
            frameLayoutContainer.bringToFront();

            if (!isShown) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, 0, 1);
                scaleAnimation.setDuration(700);
                scaleAnimation.setFillAfter(true);
                linearLayout.setAnimation(scaleAnimation);
            }
            isShown = true;
        }
    }

    public static void clearMessage() {
        if (frameLayoutContainer != null && linearLayout != null) {
            frameLayoutContainer.removeView(linearLayout);

            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, 1, 0);
            scaleAnimation.setDuration(700);
            scaleAnimation.setFillAfter(true);
            linearLayout.setAnimation(scaleAnimation);
            isShown = false;

        }
    }

    public static void clearMessageWithoutAnimation() {
        if (frameLayoutContainer != null && linearLayout != null) {
            frameLayoutContainer.removeView(linearLayout);
        }

    }

}