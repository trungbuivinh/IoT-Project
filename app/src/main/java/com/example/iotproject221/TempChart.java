package com.example.iotproject221;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class TempChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_chart);

        //Get graph from layout
        GraphView graph=(GraphView)findViewById(R.id.TempChart);

        //form series (curve for graph)
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        double y;
        for(int x=0; x<90; x++){
            y = Math.sin(2*x*0.2)-2*Math.sin(x*0.2);
            series.appendData(new DataPoint(x,y), true, 90);
        }

        graph.addSeries(series);

        // set color, title of curve, database radius, thickness
        series.setColor(Color.RED);
        series.setTitle("Temperature"); //for legend
        series.setThickness(8);

        //set title of graph
        graph.setTitle("Temperature Chart");
        graph.setTitleTextSize(90);
        graph.setTitleColor(Color.GREEN);

        //legend
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        // axis titles
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time");
        gridLabel.setHorizontalAxisTitleTextSize(50);
        gridLabel.setVerticalAxisTitle("Temperature");
        gridLabel.setVerticalAxisTitleTextSize(50);
    }
}