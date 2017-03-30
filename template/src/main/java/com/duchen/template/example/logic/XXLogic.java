package com.duchen.template.example.logic;

import com.duchen.template.concept.ILogic;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.box.XXListBox;
import com.duchen.template.example.box.model.YYItemData;
import com.duchen.template.example.box.model.ZZItemData;

public interface XXLogic extends ILogic {

    int MSG_LOAD_DATA_SUCCESS = 0x101;
    int MSG_LOAD_MORE_FAILED = 0x102;
    int MSG_LOAD_DATA_NO_CONTENT = 0x103;
    int MSG_LOAD_DATA_ERROR = 0x104;

    XXListBox.ViewModel getViewModel();

    void loadData(boolean isRefresh);

    void onClickYYItem(YYItemData yyItemData);

    void onClickZZItem(ZZItemData zzItemData);

    void onClickInside(IViewModel viewModel);
}
