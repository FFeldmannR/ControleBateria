package br.feldmann.controlebateria;
//
import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        Button btnVibrar = (Button) findViewById(R.id.btnVibrar);
        btnVibrar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                vibrar(); //chamada do metodo vibrar
            }
        });
        // EXIBE NIVEL DE BATERIA
        batteryText = (TextView) findViewById(R.id.bateria);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //
    }//fim onCreate
    public void vibrar(){
        // Obtenha uma referência ao serviço de vibração do sistema
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Verifique se o dispositivo suporta vibração
        if (vibrator.hasVibrator()) {
            // Defina a duração da vibração em milissegundos
            long duration = 500; // 0,5 segundo

            // Vibra o dispositivo pelo tempo especificado
            vibrator.vibrate(duration);
        }
    }
}//fim class