package com.example.iotproject;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.ArrayList;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HumiChart extends AppCompatActivity {
    LineChart mpLineChart;
    RelativeLayout mainLayout;
    TextView txtTemp;
    String txtHumi1;
    Float txtHumi2;
    MQTTHelper mqttHelper1;
    ArrayList dataValues2 = new ArrayList();
    int i=2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humi_chart);
        startMQTT();


        dataValues2.add(new Entry(1,0));

        mpLineChart = (LineChart) findViewById(R.id.humiChart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues2, "Humidity");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        mpLineChart.setVisibleXRangeMaximum(5);
        mpLineChart.setData(new LineData(dataSets));
        mpLineChart.invalidate();

        lineDataSet1.setCircleRadius(5);
        lineDataSet1.setDrawCircles(true);
        lineDataSet1.setCircleColor(Color.GREEN);
        lineDataSet1.setLineWidth(3);
        lineDataSet1.setValueTextSize(10);
    }

    public ArrayList<Entry> dataValues1(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(1,55));
        return dataVals;
    }

    public void startMQTT(){
        mqttHelper1 = new MQTTHelper(this);
        mqttHelper1.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.contains("dadn.cambien-doamdat")){
                    txtHumi1 = message.toString();
                    txtHumi2 = Float.parseFloat(txtHumi1);

                    dataValues2.add(new Entry(i, txtHumi2));
                    i++;

                    LineDataSet lineDataSet1 = new LineDataSet(dataValues2, "Humidity");
                    ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                    dataSets.add(lineDataSet1);

                    mpLineChart.setVisibleXRangeMaximum(5);
                    mpLineChart.moveViewToX(txtHumi2);
                    mpLineChart.setData(new LineData(dataSets));
                    mpLineChart.invalidate();


                    lineDataSet1.setCircleRadius(5);
                    lineDataSet1.setDrawCircles(true);
                    lineDataSet1.setCircleColor(Color.GREEN);
                    lineDataSet1.setLineWidth(3);
                    lineDataSet1.setValueTextSize(10);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}





