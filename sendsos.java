package com.example.myapplication;

import static android.widget.Toast.makeText;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{

    EditText phonenumber,message1;
    Button send;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        phonenumber = findViewById(R.id.phone);
        message1 = findViewById(R.id.message);
        send = findViewById(R.id.button);

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED)
               {
                   sendSMS();
               }
               else
               {
                   ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},100);

               }
            }
        });


    }

    public void OnRequestPermissionResult(int requestCode, @NonNull String []permissions,@NonNull int[]grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode==100&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            sendSMS();
        }
        else
        {
            Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
        }
    }


    private void sendSMS()
    {
        String phone = phonenumber.getText().toString();
        String message = message1.getText().toString();

        if(!phone.isEmpty()&&!message.isEmpty())
        {
            SmsManager  smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,message,null,null);
            
            Toast.makeText(this,"Recorded details",Toast.LENGTH_SHORT).show();
            

        }
        else
        {
            Toast.makeText(this,"Please enter phone and message",Toast.LENGTH_SHORT).show();

        }
    }
}