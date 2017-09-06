package com.ygst.cenggeche.ui.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.utils.SpaceItemDecoration;

/**
 * RecyclerView中的所有方法都可以在此类中设置，暴露出去以供调用
 */
public class PullRecyclerView extends PullBaseView<RecyclerView> {


    public PullRecyclerView(Context context) {
        this(context, null);
    }

    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected RecyclerView createRecyclerView(Context context, AttributeSet attrs) {
        return new RecyclerView(context, attrs);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.quan_tablayout_tv);
//        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
    }


}
