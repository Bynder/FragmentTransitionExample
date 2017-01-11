package com.getbynder.fragmenttransitionexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleFragment extends Fragment implements ViewWithSharedElements
{
    private static final String RESOURCE_LAYOUT = "RESOURCE_LAYOUT";
    private int mLayoutResourceId;

    @Nullable @BindView(R.id.logo) ImageView mLogo;
    @Nullable @BindView(R.id.button) ImageButton mButton;

    public SimpleFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Step1Fragment.
     */
    public static SimpleFragment newInstance(int resource)
    {
        SimpleFragment fragment = new SimpleFragment();
        Bundle args = new Bundle();
        args.putInt(RESOURCE_LAYOUT, resource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mLayoutResourceId = getArguments().getInt(RESOURCE_LAYOUT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(mLayoutResourceId, container, false);
        if (v != null)
        {
            ButterKnife.bind(this, v);
        }
        return v;
    }

    @Override
    public List<View> getSharedElements()
    {
        List<View> elements = new ArrayList<>();
        if (mLogo != null) elements.add(mLogo);
        if (mButton != null) elements.add(mButton);
        return elements;
    }
}
