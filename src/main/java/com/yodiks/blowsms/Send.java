package com.yodiks.blowsms;

import com.yodiks.blowsms.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Send extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        final EditText edtPhone = (EditText) findViewById(R.id.edtNoTujuan);
        final EditText edtPesan =(EditText) findViewById(R.id.edtPesan);
        final Button btnKirim = (Button) findViewById(R.id.btnSend);
        final TextView txtChiper = (TextView)  findViewById(R.id.txtChiper);
        final EditText edtKey= (EditText) findViewById(R.id.edtKey);
 
        btnKirim.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String noHP= edtPhone.getText().toString();
				String pesan = edtPesan.getText().toString();

				String strKey=edtKey.getText().toString().trim();
				String chiperText= Process.enkripsi(pesan, strKey);							
				   if(noHP.length()>0){
			        	kirimSMS(noHP, chiperText);
			        	txtChiper.setText(chiperText);
			        }else {
			        	Toast.makeText(getBaseContext(), "Enter Phone Number", Toast.LENGTH_LONG).show();
			        }
				
			}
		});
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_send_sms, menu);
        return true;
    }
    
    public void kirimSMS(String noHP, String pesan){
    	 String SENT = "SMS_SENT";
         String DELIVERED = "SMS_DELIVERED";
         PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
         PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
         //ketika SMS SENT
         registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS SENT", Toast.LENGTH_LONG).show();
	
					break;
				case android.telephony.SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(), "Error !!", Toast.LENGTH_LONG).show();
					break;
				case android.telephony.SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(), "No Signal", Toast.LENGTH_LONG).show();
					break;
				case android.telephony.SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(), "PDU NULL", Toast.LENGTH_LONG).show();
					break;
				case android.telephony.SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(), "ERROR !! Radio off!!", Toast.LENGTH_LONG).show();
					break;

				default:
					Toast.makeText(getBaseContext(), "Error !!", Toast.LENGTH_LONG).show();
				}
			}
		}, new IntentFilter(SENT));
         
         //ketika SMS DELIVERED
         registerReceiver(new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				switch (getResultCode()) {
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(), "SMS DELIVERED", Toast.LENGTH_LONG).show();
	
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(), "SMS cancel!!", Toast.LENGTH_LONG).show();
					break;
				

				default:
					Toast.makeText(getBaseContext(), "Error!!", Toast.LENGTH_LONG).show();
				}
				Toast.makeText(getBaseContext(), "yogids@ymail.com", Toast.LENGTH_LONG).show();
			}
			
		}, new IntentFilter(DELIVERED));

         android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
         sms.sendTextMessage(noHP, null, pesan, sentPI, deliveredPI);  
    	
    }
}
/* Yogi Dika Saputra
 * 4812010024 
 * IT3Aeu
 */
