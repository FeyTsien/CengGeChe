package com.ygst.cenggeche.ui.activity.guidepage;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.app.AppData;
import com.ygst.cenggeche.ui.activity.main.MainActivity1;
import com.ygst.cenggeche.ui.onboarder.AhoyOnboarderActivity;
import com.ygst.cenggeche.ui.onboarder.AhoyOnboarderCard;
import com.ygst.cenggeche.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class GradientBackgroundExampleActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("City Guide", "Detailed guides to help you plan your trip.", R.mipmap.logo);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Travel Blog", "Share your travel experiences with a vast network of fellow travellers.", R.mipmap.logo_setting);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Chat", "Connect with like minded people and exchange your travel stories.", R.drawable.chat);

        ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
        ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);

        List<AhoyOnboarderCard> pages = new ArrayList<>();

        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.grey_200);
            //page.setTitleTextSize(dpToPixels(12, this));
            //page.setDescriptionTextSize(dpToPixels(8, this));
            //page.setIconLayoutParams(width, height, marginTop, marginLeft, marginRight, marginBottom);
        }

        setFinishButtonTitle("开启蹭车之旅");
        showNavigationControls(true);
        setGradientBackground();

        //set the button style you created
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));
        }

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        setFont(face);

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        AppData.savaFirstOpen(0);
        CommonUtils.startActivity(this, MainActivity1.class);
    }
}
