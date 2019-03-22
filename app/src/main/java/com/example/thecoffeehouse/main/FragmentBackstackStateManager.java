package com.example.thecoffeehouse.main;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

@Singleton
public class FragmentBackstackStateManager {

    private FragmentManager fragmentManager;

    @Inject
    public FragmentBackstackStateManager() {
    }

    private BackstackCallback backstackCallbackImpl = new BackstackCallback() {
        @Override
        public void onFragmentPushed(Fragment parentFragment) {

            parentFragment.onPause();
        }

        @Override
        public void onFragmentPopped(Fragment parentFragment) {

            parentFragment.onResume();
        }
    };

    public FragmentBackstackChangeListenerImpl getListener() {
        return new FragmentBackstackChangeListenerImpl(fragmentManager, backstackCallbackImpl);
    }

    public void apply(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        fragmentManager.addOnBackStackChangedListener(getListener());
    }
}
