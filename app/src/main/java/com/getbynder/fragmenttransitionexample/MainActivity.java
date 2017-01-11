package com.getbynder.fragmenttransitionexample;

import android.os.Build;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity<F extends Fragment & ViewWithSharedElements> extends AppCompatActivity
{
    private static final long TRANSITION_DEFAULT_TIME = 1000;

    @BindView(R.id.coordinator_layout) CoordinatorLayout mCoordinatorLayout;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();

        loadInitialFragment();
        setDelayedLoaders();
    }

    private void setDelayedLoaders()
    {
        Handler h1 = new Handler();
        h1.postDelayed(this::transition1, 1000);

        Handler h2 = new Handler();
        h2.postDelayed(this::transition2, 3000);

        Handler h3 = new Handler();
        h3.postDelayed(this::transition3, 5000);

        Handler h4 = new Handler();
        h4.postDelayed(this::transition4, 7000);

        Handler h5 = new Handler();
        h5.postDelayed(this::transition5, 9000);
    }

    private void loadInitialFragment()
    {
        F fragment = (F) SimpleFragment.newInstance(R.layout.fragment_logo_center);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void transition1()
    {
        F fromFragment = (F) mFragmentManager.findFragmentById(R.id.fragment_container);
        List<String> transitionElements = new ArrayList<>();
        transitionElements.add(getString(R.string.logoTransitionName));
        F toFragment = (F) SimpleFragment.newInstance(R.layout.fragment_logo_top);
        performTransaction(fromFragment, toFragment, transitionElements);
    }

    private void transition2()
    {
        F fromFragment = (F) mFragmentManager.findFragmentById(R.id.fragment_container);
        List<String> transitionElements = new ArrayList<>();
        transitionElements.add(getString(R.string.logoTransitionName));
        F toFragment = (F) SimpleFragment.newInstance(R.layout.fragment_logo_bottom);
        performTransaction(fromFragment, toFragment, transitionElements);
    }

    private void transition3()
    {
        F fromFragment = (F) mFragmentManager.findFragmentById(R.id.fragment_container);
        List<String> transitionElements = new ArrayList<>();
        transitionElements.add(getString(R.string.logoTransitionName));
        transitionElements.add(getString(R.string.buttonTransitionName));
        F toFragment = (F) SimpleFragment.newInstance(R.layout.fragment_logobutton_leftright);
        performTransaction(fromFragment, toFragment, transitionElements);
    }

    private void transition4()
    {
        F fromFragment = (F) mFragmentManager.findFragmentById(R.id.fragment_container);
        List<String> transitionElements = new ArrayList<>();
        transitionElements.add(getString(R.string.logoTransitionName));
        transitionElements.add(getString(R.string.buttonTransitionName));
        F toFragment = (F) SimpleFragment.newInstance(R.layout.fragment_logobutton_bottomtop);
        performTransaction(fromFragment, toFragment, transitionElements);
    }

    private void transition5()
    {
        F fromFragment = (F) mFragmentManager.findFragmentById(R.id.fragment_container);
        List<String> transitionElements = new ArrayList<>();
        transitionElements.add(getString(R.string.logoTransitionName));
        F toFragment = (F) SimpleFragment.newInstance(R.layout.fragment_logo_center_big);
        performTransaction(fromFragment, toFragment, transitionElements);
    }

    private void performTransaction(F fromFragment, F toFragment, List<String> transitionElements)
    {
        if (fromFragment == null)
        {   // if there is no Fragment loaded, the Activity does not exist anymore
            return;
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            TransitionManager.beginDelayedTransition(mCoordinatorLayout);
        }
        else
        {
            // Enter Transition
            Fade fade = new Fade();
            fade.setStartDelay(TRANSITION_DEFAULT_TIME);
            toFragment.setEnterTransition(fade);

            // Exit Transition
            toFragment.setExitTransition(new Fade());

            // Enter Shared Elements Transition
            TransitionSet enterTransitionSet = new TransitionSet();
            enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
            enterTransitionSet.setDuration(TRANSITION_DEFAULT_TIME);
            toFragment.setSharedElementEnterTransition(enterTransitionSet);

            // Exit Shared Elements Transition
            TransitionSet exitTransitionSet = new TransitionSet();
            exitTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
            exitTransitionSet.setDuration(TRANSITION_DEFAULT_TIME);
            exitTransitionSet.setStartDelay(TRANSITION_DEFAULT_TIME);
            toFragment.setSharedElementReturnTransition(exitTransitionSet);

            for (View sharedView : fromFragment.getSharedElements())
            {
                if (transitionElements.contains(sharedView.getTransitionName()))
                {
                    fragmentTransaction.addSharedElement(sharedView, sharedView.getTransitionName());
                }
            }
        }
        fragmentTransaction.replace(R.id.fragment_container, toFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
