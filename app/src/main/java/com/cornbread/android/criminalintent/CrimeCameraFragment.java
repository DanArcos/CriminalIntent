package com.cornbread.android.criminalintent;

import android.annotation.TargetApi;
import android.graphics.Camera;
import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;

public class CrimeCameraFragment extends Fragment {
    private static final String TAG = "CrimeCameraFragment";

    private android.hardware.Camera mCamera; //Camera object
    private SurfaceView mSurfaceView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_camera, container);

        Button takePictureButton = (Button) v.findViewById(R.id.crime_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish(); //Simply finishes hosting activity this will be updated.
            }
        });

        mSurfaceView = (SurfaceView)v.findViewById(R.id.crime_camera_surfaceView);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //Tell Camera to use this surface as its preview area
                try{
                    if(mCamera != null){
                        mCamera.setPreviewDisplay(holder);
                    }
                } catch (IOException exception){
                    Log.e(TAG, "Error setting up preview display", exception);
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //We can no longer display on this surface so stop the preview
                if(mCamera != null){
                    mCamera.stopPreview();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //We can no longer display on this surface, so stop the preview
                if(mCamera == null) return;

                //The surface has changed size; update the camera preview size
                android.hardware.Camera.Parameters parameters = mCamera.getParameters();
                android.hardware.Camera.Size s = null; //To be reset
                parameters.setPreviewSize(s.width, s.height);
                mCamera.setParameters(parameters);
                try{
                    mCamera.startPreview();
                } catch (Exception e){
                    Log.e(TAG, "Could not start preview", e);
                    mCamera.release();
                    mCamera = null;
                }
            }
        });

        return v;
    }

    @TargetApi(9)
    @Override
    public void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            mCamera = android.hardware.Camera.open(0);
        } else {
            mCamera = android.hardware.Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }
}
