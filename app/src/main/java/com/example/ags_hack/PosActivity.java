package com.example.ags_hack;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

public class PosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        this.checkCameraPermission();
        this.cameraActions();
    }

    public Camera cm;

    private void cameraActions() {
        //capture an image till you get a valid face.
        int cameraId = this.findFrontFacingCamera();
        Camera camera = Camera.open(cameraId);
        this.cm = camera;
        CameraPreview pv = new CameraPreview(this,camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(pv);
//        camera.startPreview();
        new RefreshTask().execute();
    }

    private void checkCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Log.Debug("Camera found");
                cameraId = i;
                break;
            }
        }
        return cameraId;
    }

    //class which updates our textview every second

    class RefreshTask extends AsyncTask {

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(), "Capturing", Toast.LENGTH_SHORT);
            String text = String.valueOf(System.currentTimeMillis());
            //myTextView.setText(text);
            cm.takePicture(null,null,new PhotoHandler(getApplicationContext()));
        }

        @Override
        protected Object doInBackground(Object... params) {
            //while(someCondition) {
            try {
                //sleep for 1s in background...
                Thread.sleep(3000);
                //and update textview in ui thread
                publishProgress();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
            return null;
            //}
        }
    }

}
