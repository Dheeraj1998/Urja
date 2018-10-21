package io.github.dheeraj1998.oorja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.enums.Anchor;
import com.anychart.enums.HAlign;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    private Double currentValue = 10D;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("sensor_data");
        Query lastQuery = myRef.orderByKey().limitToLast(1);

        //region CircularGuage code
        final CircularGauge circularGauge = AnyChart.circular();
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0, 0, 0, 0)
                .margin(30, 30, 30, 30);
        circularGauge.startAngle(0)
                .sweepAngle(360);

        circularGauge.data(new SingleValueDataSet(new Double[] { currentValue }));

        circularGauge.axis(0)
                .startAngle(-150)
                .radius(80)
                .sweepAngle(300)
                .width(3)
                .ticks("{ type: 'line', length: 4, position: 'outside' }");

        circularGauge.axis(0).labels().position("outside");

        circularGauge.axis(0).scale()
                .minimum(0)
                .maximum(1);

        circularGauge.axis(0).scale()
                .ticks("{interval: 0.1}")
                .minorTicks("{interval: 0.01}");

        circularGauge.needle(0)
                .stroke(null)
                .startRadius("6%")
                .endRadius("38%")
                .startWidth("2%")
                .endWidth(0);

        circularGauge.cap()
                .radius("4%")
                .enabled(true)
                .stroke(null);

        circularGauge.label(0)
                .text(" <br> Wh Used")
                .useHtml(true)
                .hAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(0)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(100)
                .offsetX(6)
                .padding(15, 20, 0, 0);

        circularGauge.label(1)
                .text("" + currentValue.toString())
                .useHtml(true)
                .hAlign(String.valueOf(HAlign.CENTER));
        circularGauge.label(1)
                .anchor(Anchor.CENTER_TOP)
                .offsetY(-100)
                .padding(5, 10, 0, 10)
                .background("{fill: 'none', stroke: '#c1c1c1', corners: 3, cornerType: 'ROUND'}");

        circularGauge.range(0,
                "{\n" +
                        "    from: 0,\n" +
                        "    to: 0.3,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'green 0.5',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        circularGauge.range(1,
                "{\n" +
                        "    from: 0.7,\n" +
                        "    to: 1,\n" +
                        "    position: 'inside',\n" +
                        "    fill: 'red 0.5',\n" +
                        "    stroke: '1 #000',\n" +
                        "    startSize: 6,\n" +
                        "    endSize: 6,\n" +
                        "    radius: 80,\n" +
                        "    zIndex: 1\n" +
                        "  }");

        anyChartView.setChart(circularGauge);
        //endregion

        //region Reading from the database
        lastQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        currentValue = Double.parseDouble(String.valueOf(snapshot.child("pulse").getValue()));
                    }

                    catch (Exception error) {
                        currentValue = 0.3D;
                    }

                    APIlib.getInstance().setActiveAnyChartView(anyChartView);
                    circularGauge.data(new SingleValueDataSet(new Double[] { currentValue }));
                    circularGauge.label(1)
                            .text(currentValue.toString())
                            .useHtml(true)
                            .hAlign(String.valueOf(HAlign.CENTER));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed to read value.", error.toException());
            }
        });
        //endregion
    }

    public void intentChatbot(View view) {
        Intent temp = new Intent(Dashboard.this, ChatActivity.class);
        startActivity(temp);
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
