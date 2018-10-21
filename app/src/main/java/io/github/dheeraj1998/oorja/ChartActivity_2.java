package io.github.dheeraj1998.oorja;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Bar;
import com.anychart.core.cartesian.series.JumpLine;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.util.ArrayList;
import java.util.List;

public class ChartActivity_2 extends AppCompatActivity {

    private AnyChartView anyChartView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_2);

        anyChartView1 = findViewById(R.id.any_chart_view_2);
        makeGraph1();
    }

    public void makeGraph1(){
        //region 1st Graph
        Cartesian vertical = AnyChart.vertical();

        vertical.animation(true)
                .title("Weekly Analysis");

        List<DataEntry> data = new ArrayList<>();
        data.add(new CustomDataEntry("Mon", 11.5, 9.3));
        data.add(new CustomDataEntry("Tue", 12, 10.5));
        data.add(new CustomDataEntry("Wed", 11.7, 11.2));
        data.add(new CustomDataEntry("Thu", 12.4, 11.2));
        data.add(new CustomDataEntry("Fri", 13.5, 12.7));
        data.add(new CustomDataEntry("Sat", 11.9, 13.1));
        data.add(new CustomDataEntry("Sun", 14.6, 12.2));

        Set set = Set.instantiate();
        set.data(data);
        Mapping barData = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping jumpLineData = set.mapAs("{ x: 'x', value: 'jumpLine' }");

        Bar bar = vertical.bar(barData);
        bar.labels().format("{%Value} Wh");

        JumpLine jumpLine = vertical.jumpLine(jumpLineData);
        jumpLine.stroke("2 #60727B");
        jumpLine.labels().enabled(false);

        vertical.yScale().minimum(0d);

        vertical.labels(true);

        vertical.tooltip()
                .displayMode(TooltipDisplayMode.UNION)
                .positionMode(TooltipPositionMode.POINT)
                .unionFormat(
                        "function() {\n" +
                                "      return 'Plain: ' + this.points[1].value + ' Wh' +\n" +
                                "        '\\n' + 'Fact: ' + this.points[0].value + ' Wh';\n" +
                                "    }");

        vertical.interactivity().hoverMode(HoverMode.BY_X);

        vertical.xAxis(true);
        vertical.yAxis(true);
        vertical.yAxis(0).labels().format("{%Value} Wh");

        anyChartView1.setChart(vertical);
        //endregion
    }

    private class CustomDataEntry extends ValueDataEntry {
        public CustomDataEntry(String x, Number value, Number jumpLine) {
            super(x, value);
            setValue("jumpLine", jumpLine);
        }
    }
}
