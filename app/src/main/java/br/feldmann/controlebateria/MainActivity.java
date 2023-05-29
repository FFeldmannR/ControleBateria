package br.feldmann.controlebateria;
//
import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
//
public class MainActivity extends AppCompatActivity {
    private TextView batteryText;
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            //
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level * 100 / (float)scale;
            batteryText.setText( batteryPct + "%" );
        }
    };
    //
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Construtor", "onCreate");
        //

        // EXIBE NIVEL DE BATERIA
        batteryText = (TextView) findViewById(R.id.bateria);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //
    }//fim onCreate
}//fim class