
package com.example.yg.sms_auto_registration;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS)){
            Toast.makeText(this, "SMS 권한 설정이 필요함", Toast.LENGTH_SHORT).show();
        } else {
            // 권한이 할당되지 않았으면 해당 권한을 요청
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.RECEIVE_SMS},1);
        }

        button = (Button)findViewById(R.id.testButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectFireBaseDB.write("미나", 23);
            }
        });


    }
}
