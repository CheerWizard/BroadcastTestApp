package com.example.jeremy.broadcasttestapp.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeremy.broadcasttestapp.IContract;
import com.example.jeremy.broadcasttestapp.R;
import com.example.jeremy.broadcasttestapp.constants.AnimationType;
import com.example.jeremy.broadcasttestapp.constants.IntentActions;
import com.example.jeremy.broadcasttestapp.constants.RequestCodes;
import com.example.jeremy.broadcasttestapp.constants.Schemas;
import com.example.jeremy.broadcasttestapp.presenters.PizzaPresenter;

public class MainActivity extends AppCompatActivity implements IContract.IView, View.OnClickListener {
    //presenter
    private PizzaPresenter pizzaPresenter;
    //broadcast receivers
    private BroadcastReceiver pizzaBroadcastReceiver;
    //views
    private TextView orderStateTextView;
    private Button cancelButton , orderButton , callButton;
    //handler
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVars();
        registerReceivers();
        initListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceivers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pizzaPresenter.onClear();
    }

    private void initViews() {
        //text_views
        final TextView pizzaNameTextView = findViewById(R.id.pizza_name_text_view);
        final TextView pizzaDescriptionLabelTextView = findViewById(R.id.description_text_view);
        final TextView pizzaDescriptionTextView = findViewById(R.id.description_text);
        final TextView pizzaIngredientsTextView = findViewById(R.id.ingredients_text);
        final TextView pizzaIngredientsLabelTextView = findViewById(R.id.ingredients_text_view);
        orderStateTextView = findViewById(R.id.order_status_text_view);
        //buttons
        callButton = findViewById(R.id.call_btn);
        cancelButton = findViewById(R.id.cancel_btn);
        orderButton = findViewById(R.id.order_btn);
        //image_views
        final ImageView pizzaImageView = findViewById(R.id.photo_image_view);
    }

    private void initVars() {
        handler = new Handler();
        pizzaPresenter = new PizzaPresenter(this);
    }

    private void initListeners() {
        cancelButton.setOnClickListener(this);
        callButton.setOnClickListener(this);
        orderButton.setOnClickListener(this);
    }

    @Override
    public void animate(AnimationType animationType) {
        switch (animationType) {
            case ANIMATE_ORDERED:
                orderStateTextView.setText(getResources().getString(R.string.ordered));
                orderStateTextView.setTextColor(getResources().getColor(R.color.green));
                break;
            case ANIMATE_NOT_ORDERED:
                orderStateTextView.setText(getResources().getString(R.string.not_ordered));
                orderStateTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                break;
            case ANIMATE_ORDERING:
                orderStateTextView.setText(getResources().getString(R.string.ordering));
                orderStateTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                pizzaPresenter.onTouchCancelBtn();
                break;
            case R.id.call_btn:
                makeCall();
                break;
            case R.id.order_btn:
                sendBroadcast();
                startBroadcast();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.REQUEST_CODE_CALL:
                    Toast.makeText(this, R.string.called_success, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        else if (resultCode == RESULT_CANCELED) Toast.makeText(this, R.string.call_cancelled, Toast.LENGTH_SHORT).show();
    }

    private void registerReceivers() {
        registerReceiver(pizzaBroadcastReceiver , new IntentFilter(IntentActions.ACTION_ORDER_PIZZA));
    }

    private void unregisterReceivers() {
        unregisterReceiver(pizzaBroadcastReceiver);
    }

    private void makeCall() {
        startActivityForResult(new Intent(Intent.ACTION_DIAL , Uri.fromParts(
                Schemas.TEL_SHEMA ,
                getResources().getString(R.string.phone_number) ,
                null)) , RequestCodes.REQUEST_CODE_CALL);
    }

    private void sendBroadcast() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendBroadcast(new Intent(IntentActions.ACTION_ORDER_PIZZA));
            }
        }).start();
    }

    private void startBroadcast() {
        animate(AnimationType.ANIMATE_ORDERING);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pizzaBroadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        pizzaPresenter.onTouchOrderBtn();
                    }
                };
            }
        } , 3000);
    }
}
