package com.example.juankno4.geolocalizacion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LocationManager lm;
    LocationProvider lp;
    LocationListener ls;
    TextView text1, text2;
    Button btn;
    private int REQUEST_CODE = 100;
    private double latitude = 25.5011, longitude = -103.3875,latitude1=25.5012, longitude1=-103.3876,f1,f2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // se toma los elementos de el layout
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        btn = findViewById(R.id.btn1);
        //p
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lp = lm.getProvider(LocationManager.GPS_PROVIDER);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Estas dando click en el boton", Toast.LENGTH_LONG).show();
            }
        });
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "No esta activo el gps", Toast.LENGTH_SHORT).show();
            return;
        }

        ls = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                DecimalFormat df = new DecimalFormat("####.0000");
                f1 = location.getLatitude();
                f2 = location.getLongitude();
                text1.setText(df.format(f1));
                text2.setText(df.format(f2));
                if (df.format(f1).equals(df.format(latitude)) && df.format(f2).equals(df.format(longitude)) || df.format(f1).equals(df.format(latitude1)) || df.format(f2).equals(df.format(longitude1))) {
                    btn.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Estas en el lugar correcto", Toast.LENGTH_SHORT).show();

                } else {
                    btn.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Estas Fuera de rango", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                // poner mensaje con log.d(call/msg)
            }

            @Override
            public void onProviderDisabled(String provider) {
                // poner mensaje con log.d(call/msg)
            }
        };
        // verifica el permiso de la aplicaion esta activa
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            Toast.makeText(this, "dddc", Toast.LENGTH_LONG).show();
            return;
        }
        // el 0 son 2 ariables que ,e sirven 1 especificar el tiempo de la ubicacion y la otra sin distancia de la geolocalizacion (Distancia)
        // se le pasa ls es el que va a tener la locacion
        // detecta cada cuando se haga un cambion se captura dentro de su metodo
        /*permisos (revisa permisos y solo pide al usuario si es necesario, foquito, add permissions check)
         * Activitycompat.requestPermissions(this,new String[]
         * */
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ls);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            //
        }
    }
}
