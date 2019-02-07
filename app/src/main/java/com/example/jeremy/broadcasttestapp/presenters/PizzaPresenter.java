package com.example.jeremy.broadcasttestapp.presenters;

import com.example.jeremy.broadcasttestapp.IContract;
import com.example.jeremy.broadcasttestapp.R;
import com.example.jeremy.broadcasttestapp.constants.AnimationType;
import com.example.jeremy.broadcasttestapp.constants.OrderStates;
import com.example.jeremy.broadcasttestapp.models.Pizza;
import com.example.jeremy.broadcasttestapp.ui.activities.PizzaApplication;

public class PizzaPresenter implements IContract.IPresenter {

    private IContract.IView view;
    private Pizza pizza;

    public PizzaPresenter(IContract.IView view) {
        this.view = view;
        pizza = new Pizza(PizzaApplication.getInstance().getResources().getString(R.string.pizza_name) ,
                PizzaApplication.getInstance().getResources().getString(R.string.pizza_description) ,
                PizzaApplication.getInstance().getResources().getString(R.string.pizza_ingredients) ,
                21.69 , OrderStates.NOT_ORDERED);
    }

    public void onTouchOrderBtn() {
        pizza.setOrderStates(OrderStates.ORDERED);
        view.animate(AnimationType.ANIMATE_ORDERED);
    }

    public void onTouchCancelBtn() {
        pizza.setOrderStates(OrderStates.NOT_ORDERED);
        view.animate(AnimationType.ANIMATE_NOT_ORDERED);
    }

    @Override
    public void onClear() {
        //empty method
        pizza = null;
    }
}
