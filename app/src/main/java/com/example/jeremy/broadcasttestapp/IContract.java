package com.example.jeremy.broadcasttestapp;

import android.content.Context;

import com.example.jeremy.broadcasttestapp.constants.AnimationType;

public interface IContract {

    interface IView {
        void animate(AnimationType animationType);
    }

    interface IPresenter {
        void onClear();
    }
}
