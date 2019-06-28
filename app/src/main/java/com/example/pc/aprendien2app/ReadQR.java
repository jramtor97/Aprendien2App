package com.example.pc.aprendien2app;

import android.Manifest;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ReadQR extends BasicActivity {

    private SurfaceView cameraPreview;
    private TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
   final int RequestCameraPermissionID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_qr);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbarQr);
        setSupportActionBar(toolbar);

        cameraPreview = (SurfaceView) findViewById(R.id.camera_preview);
        txtResult = (TextView) findViewById(R.id.txt_preview);
        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(640, 480).build();
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ReadQR.this, new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }

                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode>  qrcode = detections.getDetectedItems();

                if(qrcode.size()!=0) {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            Button btn = (Button)findViewById(R.id.confirmQR);
                            btn.setEnabled(true);
                            txtResult.setText(qrcode.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });

        Button btnOK =(Button)findViewById(R.id.confirmQR);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),MainActivity.class);
                Toast.makeText(v.getContext(),"Success",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public String getActivityText() {
        return "Hola";
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

}