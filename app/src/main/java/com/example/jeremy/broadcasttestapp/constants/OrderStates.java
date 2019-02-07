package com.example.jeremy.broadcasttestapp.constants;

import android.support.annotation.NonNull;

public enum OrderStates {
    ORDERED,
    NOT_ORDERED;

    @NonNull
    @Override
    public String toString() {
        String state;
        switch (this) {
            case ORDERED:
                state = "Ordered";
                break;
            case NOT_ORDERED:
                state = "Not ordered";
                break;
                default: state = "";
        }
        return state;
    }
}
