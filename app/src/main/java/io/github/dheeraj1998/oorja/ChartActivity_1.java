package io.github.dheeraj1998.oorja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity_1 extends AppCompatActivity {

    private AnyChartView anyChartView1;
    private AnyChartView anyChartView2;
    private AnyChartView anyChartView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_1);

        anyChartView1 = findViewById(R.id.any_chart_view_1);

        makeGraph1();
    }

    public void makeGraph1(){
        //region 1st Graph
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Electricity usage for the last 3 days");

        cartesian.yAxis(0).title("Power consumed");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        seriesData.add(new CustomDataEntry("07:00", 3.6, 2.3, 2.8));
        seriesData.add(new CustomDataEntry("08:00", 7.1, 4.0, 4.1));
        seriesData.add(new CustomDataEntry("09:00", 8.5, 6.2, 5.1));
        seriesData.add(new CustomDataEntry("10:00", 9.2, 11.8, 6.5));
        seriesData.add(new CustomDataEntry("11:00", 10.1, 13.0, 12.5));
        seriesData.add(new CustomDataEntry("12:00", 11.6, 13.9, 18.0));
        seriesData.add(new CustomDataEntry("13:00", 16.4, 18.0, 21.0));
        seriesData.add(new CustomDataEntry("14:00", 18.0, 23.3, 20.3));
        seriesData.add(new CustomDataEntry("15:00", 13.2, 24.7, 19.2));
        seriesData.add(new CustomDataEntry("16:00", 12.0, 18.0, 14.4));
        seriesData.add(new CustomDataEntry("17:00", 4.216, 15.1, 9.2));
        seriesData.add(new CustomDataEntry("18:00", 4.1, 11.3, 5.9));
        seriesData.add(new CustomDataEntry("19:00", 6.3, 14.2, 5.2));
        seriesData.add(new CustomDataEntry("20:00", 9.4, 13.7, 4.7));
        seriesData.add(new CustomDataEntry("21:00", 11.5, 9.9, 4.2));
        seriesData.add(new CustomDataEntry("22:00", 13.5, 12.1, 1.2));

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("3 days prior");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("2 days prior");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Yesterday");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView1.setChart(cartesian);
        //endregion
    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }
}
