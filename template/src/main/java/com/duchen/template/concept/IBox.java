package com.duchen.template.concept;

import android.view.View;


public interface IBox<T extends IViewModel> {

    View asView();

    void bindViewModel(T viewModel);

    void update();
}
