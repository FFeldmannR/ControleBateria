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
            verificaSeCarregando( batteryPct );
            batteryText.setText( batteryPct + "%" );
        }
    };
    //
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Construtor", "onCreate");
        // EXIBE NIVEL DE BATERIA
        batteryText = (TextView) findViewById(R.id.bateria);
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //
        //
        /* teste de vibração com botão
        Button btnVibrar = (Button) findViewById(R.id.btnVibrar);
        btnVibrar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                vibrar(); //chamada do metodo vibrar
            }
        }); */
    }//fim onCreate
    //
    public void verificaSeCarregando( float nivelBateria ){
        // Obtenha o contexto do aplicativo
        Context context = getApplicationContext();

        // Crie um IntentFilter para capturar a ação de alteração do estado da bateria
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        // Registre o receptor de broadcast para receber informações sobre a bateria
        Intent batteryStatus = context.registerReceiver(null, intentFilter);

        // Verifique se o dispositivo está conectado à fonte de energia
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        if (isCharging) {
            // O dispositivo está carregando
            Log.d("LogTag", "esta carregando");
            Log.d("LogTag", "bateria: " + nivelBateria );
            if ( nivelBateria >= 80){
                //chama vibrar
                Log.d("LogTag", "bateria carregada");
                do {
                    vibrar();
                }while (isCharging);
            }
        } else {
            // O dispositivo não está carregando
            Log.d("LogTag", "NÃO esta carregando");
        }
    }
    //
    public void vibrar(){
        // Obtenha uma referência ao serviço de vibração do sistema
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Verifique se o dispositivo suporta vibração
        if (vibrator.hasVibrator()) {
            // Defina a duração da vibração em milissegundos
            long duration = 1000; // 0,5 segundo

            // Vibra o dispositivo pelo tempo especificado
            vibrator.vibrate(duration);
        }
    }
}//fim class