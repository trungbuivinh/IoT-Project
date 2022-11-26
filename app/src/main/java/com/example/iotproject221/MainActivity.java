package com.example.iotproject221;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    MQTTHelper mqttHelper;
    TextView txtTemp, txtHumid, txtLight, txtAi;
    LabeledSwitch btnLED, btnPUMP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTemp = findViewById(R.id.txtTemperature);
        txtHumid = findViewById(R.id.txtHumidity);
        txtLight = findViewById(R.id.txtlight);
        txtAi = findViewById(R.id.txtAi);

        btnLED = findViewById(R.id.btnLED);
        btnPUMP = findViewById(R.id.btnPUMP);

        Button btnTempChart = (Button)findViewById(R.id.btnTempChart);

        btnTempChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int5=new Intent(MainActivity.this, TempChart.class);
                startActivity(int5);
            }
        });

        btnLED.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("tranngocminhdiep/feeds/nutnhan1","1");
                }else{
                    sendDataMQTT("tranngocminhdiep/feeds/nutnhan1","0");
                }
            }
        });
        btnPUMP.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("tranngocminhdiep/feeds/nutnhan2","1");
                }else{
                    sendDataMQTT("tranngocminhdiep/feeds/nutnhan2","0");
                }
            }
        });

        startMQTT();
    }

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }
    public void startMQTT(){
    mqttHelper = new MQTTHelper(this);
    mqttHelper.setCallback(new MqttCallbackExtended() {
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {

        }

        @Override
        public void connectionLost(Throwable cause) {

        }

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            Log.d("TEST", topic + "***" +message.toString());
            if(topic.contains("cambien1")){
                txtTemp.setText(message.toString() + "°C");
            } else if(topic.contains("cambien3")){
                txtHumid.setText(message.toString() + "%");
            } else if(topic.contains("cambien2")){
                txtLight.setText(message.toString() + "Lux");
            } else if(topic.contains("nutnhan1")){
                if(message.toString().equals("1")){
                    btnLED.setOn(true);
                }else{
                    btnLED.setOn(false);
                }
            } else if(topic.contains("nutnhan2")) {
                if (message.toString().equals("1")) {
                    btnPUMP.setOn(true);
                } else {
                    btnPUMP.setOn(false);
                }
            } else if(topic.contains("ai")){
                txtAi.setText(message.toString());
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {

        }
    });
    }
}