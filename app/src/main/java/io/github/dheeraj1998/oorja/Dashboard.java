package io.github.dheeraj1998.oorja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void intentHistory(View view) {
        Intent temp = new Intent(Dashboard.this, ChartActivity_1.class);
        startActivity(temp);
    }

    public void intentDA(View view) {
        Intent temp = new Intent(Dashboard.this, ChartActivity_3.class);
        startActivity(temp);
    }

    public void intentWA(View view) {
        Intent temp = new Intent(Dashboard.this, ChartActivity_2.class);
        startActivity(temp);
    }
}
