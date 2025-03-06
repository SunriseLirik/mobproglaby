package com.example.lab6penzev;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class StopwatchFragment extends Fragment {

    private static final String TAG = "StopwatchFragment";
    private TextView tvStopwatch;
    private Handler handler;
    private Runnable runnable;
    private int seconds = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        tvStopwatch = view.findViewById(R.id.tvStopwatch);

        Log.d(TAG, "Секундомер запущен");
        startStopwatch();

        return view;
    }

    private void startStopwatch() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
                tvStopwatch.setText(time);
                Log.d(TAG, "Прошло времени: " + time);
                seconds++;
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        Log.d(TAG, "Секундомер остановлен");
    }
}