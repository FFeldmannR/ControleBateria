package br.feldmann.controlebateria;
//
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//
public class BatteryReceiver extends BroadcastReceiver {
    private int bateria;
    private TextView tvBateria;
    //
    public int getBateria() { return bateria; }
    public void setTextView(TextView tvBateria){
        this.tvBateria = tvBateria;
    }
    //
    @Override public void onReceive(Context context, Intent intent) {
        //
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float)scale;
        this.bateria = (int) batteryPct;
        Log.d("LogTag", "bateria em receiver: "+batteryPct);

        // EXIBE NIVEL DE BATERIA
        Log.d("LogTag", "bateria no textview: "+ batteryPct );
        tvBateria.setText( batteryPct + "%" );
        //
        //
        if (isCharging && batteryPct == 70){ //teste com menos bateria (70%)
            // O dispositivo está carregando e a bateria atingiu 100%

            // Exemplo de alerta via Toast
            Toast.makeText(context, "Bateria em 100%!", Toast.LENGTH_LONG).show();

            // Exemplo de vibração incessante
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                vibrator.vibrate(new long[]{0, 1000}, 0);
            }
        }else {
            Toast.makeText(context, "Bateria Carregando", Toast.LENGTH_LONG).show();
        }
        //
    }//fim metodo onReceive
}//fim class
