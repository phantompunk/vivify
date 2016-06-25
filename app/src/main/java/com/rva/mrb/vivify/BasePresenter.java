package com.rva.mrb.vivify;

/**
 * Created by rigo on 6/25/16.
 */
public interface BasePresenter<T> {
    void setView(T view);
    void clearView();
    void closeRealm();
}
