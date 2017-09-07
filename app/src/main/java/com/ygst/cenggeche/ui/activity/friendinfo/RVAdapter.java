package com.ygst.cenggeche.ui.activity.friendinfo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ygst.cenggeche.R;
import com.ygst.cenggeche.ui.view.ikninephotoview.IKNinePhotoView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Idtk on 2017/3/9.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 :
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHolder> {

    private Context context;
    private List<String> mListPic;

    public RVAdapter(Context context,List<String> listPic) {
        super();
        this.context = context;
        this.mListPic = listPic;
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv,parent,false);
        RVHolder rvHolder = new RVHolder(view);
        return rvHolder;
    }

    @Override
    public void onBindViewHolder(RVHolder holder, int position) {
        MyAdapter adapter = new MyAdapter(context,mListPic);
        holder.mNinePhoto.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class RVHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.nine_photo)
        IKNinePhotoView mNinePhoto;
        public RVHolder(View itemView) {
            super(itemView);
            mNinePhoto = (IKNinePhotoView) itemView.findViewById(R.id.nine_photo);
        }
    }
}
