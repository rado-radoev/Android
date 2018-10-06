package com.superlamer.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter BA;
    Button turnBluetoothOff;
    Button findDiscoverableDevices;
    Button viewPairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        turnBluetoothOff = (Button) findViewById(R.id.turnBluetoothOff);
        findDiscoverableDevices = (Button) findViewById(R.id.findDiscoverableDevices);
        viewPairedDevices = (Button) findViewById(R.id.viewPairedDevices);
        
        if (BA.isEnabled()) {
            Toast.makeText(this, "Bluettoth Enabled", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
            
            if (BA.isEnabled()) {
                Toast.makeText(this, "Bluetooth is now being turned on", Toast.LENGTH_SHORT).show();
            }
        }
        
    }

    public void viewPairedDevices(View view) {
        Set<BluetoothDevice> pairedDevices = BA.getBondedDevices();
        ListView devicesListView = (ListView) findViewById(R.id.devicesListView);
        ArrayList<String> pairedDevicesArrayList = new ArrayList();

        for (BluetoothDevice btd : pairedDevices) {
            pairedDevicesArrayList.add(btd.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pairedDevicesArrayList);
        devicesListView.setAdapter(arrayAdapter);
    }

    public void findDiscoverableDevices(View view) {
        Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(i);
    }

    public void turnBluetoothOff(View view) {
        BA.disable();

        if (BA.isEnabled()) {
            Toast.makeText(this, "Bluettoth could not be disabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bluettoth Disabled", Toast.LENGTH_SHORT).show();
        }
    }
}
