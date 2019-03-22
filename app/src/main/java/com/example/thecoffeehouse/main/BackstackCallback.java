package com.example.thecoffeehouse.main;


import androidx.fragment.app.Fragment;

public interface BackstackCallback {

    void onFragmentPushed(Fragment parentFragment);

    void onFragmentPopped(Fragment parentFragment);
}
