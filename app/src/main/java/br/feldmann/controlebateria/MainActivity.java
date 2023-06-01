package br.feldmann.controlebateria;
//
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
//
public class MainActivity extends AppCompatActivity {
    private BatteryReceiver batteryReceiver;
    //
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Construtor", "onCreate");
        //
        batteryReceiver = new BatteryReceiver();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        batteryReceiver.setTextView( findViewById(R.id.bateria) );

        registerReceiver(batteryReceiver, intentFilter);
    }//fim onCreate
}//fim class