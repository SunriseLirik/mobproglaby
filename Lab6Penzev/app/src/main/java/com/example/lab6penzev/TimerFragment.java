package com.example.lab6penzev;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class TimerFragment extends Fragment {

    private static final String TAG = "TimerFragment";
    private TextView tvTimer;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);
        tvTimer = view.findViewById(R.id.tvTimer);

        Log.d(TAG, "Таймер запущен");
        startTimer(60000); // 60 секунд

        return view;
    }

    private void startTimer(long millisInFuture) {
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                String time = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
                tvTimer.setText(time);
                Log.d(TAG, "Осталось времени: " + time);
            }

            @Override
            public void onFinish() {
                tvTimer.setText("00:00:00");
                Log.d(TAG, "Таймер завершен");
            }
        }.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Log.d(TAG, "Таймер остановлен");
    }
}