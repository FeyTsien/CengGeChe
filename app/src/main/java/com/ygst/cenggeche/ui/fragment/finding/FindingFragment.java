package com.ygst.cenggeche.ui.fragment.finding;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseFragment;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FindingFragment extends MVPBaseFragment<FindingContract.View, FindingPresenter> implements FindingContract.View {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_finding, container, false);
    }
}
