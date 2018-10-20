package io.github.dheeraj1998.oorja;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class IntroActivity extends MaterialIntroActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Intro);

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.intro_bg_1)
                .buttonsColor(R.color.intro_btn_1)
                .image(R.drawable.app_icon)
                .title("Oorja")
                .description("Personalised energy assistant at your fingerprints.")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.intro_bg_2)
                .buttonsColor(R.color.intro_btn_2)
//                .image(R.drawable.intro_1)
                .title("[FEATURE 1]")
                .description("[About feature]")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.intro_bg_3)
                .buttonsColor(R.color.intro_btn_3)
//                .image(R.drawable.intro_2)
                .title("[FEATURE 2]")
                .description("[About feature]")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.intro_bg_4)
                .buttonsColor(R.color.intro_btn_4)
//                .image(R.drawable.intro_3)
                .title("[FEATURE 3]")
                .description("[About feature]")
                .build());
    }

    @Override
    public void onFinish() {
        Intent temp = new Intent(this, ChatActivity.class);
        startActivity(temp);
        finish();
    }
}