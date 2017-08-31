package com.ygst.cenggeche.ui.fragment.message;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.utils.LogUtils;
import com.ygst.cenggeche.R;
import com.ygst.cenggeche.mvp.MVPBaseFragment;
import com.ygst.cenggeche.ui.activity.main.MainActivity;
import com.ygst.cenggeche.ui.activity.mychat.MyChatActivity;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenu;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuCreator;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuItem;
import com.ygst.cenggeche.ui.view.swipemenulistview.SwipeMenuListView;
import com.ygst.cenggeche.utils.JMessageUtils;
import com.ygst.cenggeche.utils.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import cn.jmessage.android.uikit.chatting.ChatActivity;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Administrator on 2017/8/16.
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View {
    private String TAG = "MessageFragment";
    private Activity mContext;
    private View mRootView;
    List<Conversation> mListConversation;

    private SwipeMenuListViewAdapter mSwipeMenuListViewAdapter;
    private SwipeMenuListView mListView;
    // step 1. create a MenuCreator
    SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            //添加了一个Open侧滑按钮
            SwipeMenuItem isReadItem = new SwipeMenuItem(getActivity().getApplicationContext());
            // set item background
            isReadItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                    0xCE)));
            // set item width
            isReadItem.setWidth(dp2px(90));
            // set item title
            isReadItem.setTitle("标为已读");
            // set item title fontsize
            isReadItem.setTitleSize(18);
            // set item title font color
            isReadItem.setTitleColor(Color.WHITE);
            // add to menu
            menu.addMenuItem(isReadItem);

            // 创建delete侧滑按钮
            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity().getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            // set item width
            deleteItem.setWidth(dp2px(90));
            // set a icon
            deleteItem.setIcon(R.drawable.ic_delete);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtils.i(TAG, "------------onCreate");
        //生命周期2：onCreate()；
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
        mContext = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.i(TAG, "------------onCreateView");
        //生命周期3：onCreateView()；
        mRootView = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(getActivity());
        initView();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i(TAG, "------------onResume");
        initConversationListView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
    }

    private EditText mEditTextTargetId;
    private Button mBtnGotoChatting;

    private void initView() {

        mEditTextTargetId = (EditText) mRootView.findViewById(R.id.et_target_id);
        mBtnGotoChatting = (Button) mRootView.findViewById(R.id.btn_goto_chatting);
        mBtnGotoChatting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                intent.putExtra(JMessageUtils.TARGET_USERNAME, mEditTextTargetId.getText().toString());
                intent.setClass(mContext, MyChatActivity.class);
                startActivity(intent);
            }
        });

        mListView = (SwipeMenuListView) mRootView.findViewById(R.id.listView);
        // set creator
        mListView.setMenuCreator(creator);
        // 滑动某一个Item
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {
                // swipe start
                LogUtils.i(TAG, "swipe start");
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
                LogUtils.i(TAG, "swipe start");
            }
        });
        //点击侧滑出来的菜单按钮
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                Conversation item = mListConversation.get(position);
                switch (index) {
                    case 0:
                        // open
                        setIsRead(menu,item);
                        break;
                    case 1:
                        // delete删除某个会话
                        mPresenter.deleteConversation(item, position);
                        break;
                }
            }
        });
        //单点某一个Item
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Conversation conversation = mListConversation.get(position);
                final Intent intent = new Intent();

                if (conversation.getType().equals(ConversationType.single)){
                    String targetId = ((UserInfo) conversation.getTargetInfo()).getUserName();
                    intent.putExtra(JMessageUtils.TARGET_USERNAME, targetId);
                    intent.putExtra(JMessageUtils.TARGET_APP_KEY, conversation.getTargetAppKey());
                    intent.setClass(mContext, MyChatActivity.class);
                    startActivity(intent);
                } else{
                    long groupId = ((GroupInfo) conversation.getTargetInfo()).getGroupID();
                    intent.putExtra(JMessageUtils.GROUP_ID_KEY, groupId);
                    intent.setClass(mContext, MyChatActivity.class);
                    startActivity(intent);
                }

            }
        });
        // 长按某一个Item
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
//				Toast.makeText(getApplicationContext(), position + " long click",0).show();
                return false;
            }
        });
    }


    private void initConversationListView(){
        mListConversation = JMessageClient.getConversationList();
        mSwipeMenuListViewAdapter = new SwipeMenuListViewAdapter(getActivity(),mListConversation);
        if (mListConversation != null) {
            mListView.setAdapter(mSwipeMenuListViewAdapter);
        }
        if(mSwipeMenuListViewAdapter!=null){
            mSwipeMenuListViewAdapter.notifyDataSetChanged();
        }
    }

    private void setIsRead(SwipeMenu menu,Conversation conversation) {
        if(conversation.getUnReadMsgCnt()>0) {
            conversation.setUnReadMessageCnt(0);
        }
        MainActivity mainActivity = (MainActivity ) getActivity();
        mainActivity.showAllUnReadMsgCount();
        mSwipeMenuListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDeleteConversationSuccess(ConversationType type, int position) {
        mListConversation.remove(position);
        mSwipeMenuListViewAdapter.notifyDataSetChanged();
        if (type.equals(ConversationType.single)) {
            ToastUtil.show(getActivity(), "删除单聊会话成功");
        } else {
            ToastUtil.show(getActivity(), "删除群聊会话成功");
        }
    }

    @Override
    public void getDeleteConversationError() {
        ToastUtil.show(getActivity(), "删除会话失败");
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    /**
     * 消息事件实体类 MessageEvent
     *(之前是onEvent(),改成了onEventMainThread()主线程模式)
     * @param event
     */
    public void onEventMainThread(MessageEvent event) {
        initConversationListView();
    }

}
