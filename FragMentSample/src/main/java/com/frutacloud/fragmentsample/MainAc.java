package com.frutacloud.fragmentsample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainAc extends AppCompatActivity implements View.OnClickListener, FriendFragment.IFragmentSetValue {

    private static final String TAG = "MainAc";
    private Toolbar toolbar;

    private LinearLayout bottom;

    private TextView tab_home;
    private TextView tab_friend;

    private FrameLayout fl_content;

    private Fragment currentFragment = null;

    private HomeFragment homeFm;
    private FriendFragment friendFm;
    private MessageFragment messageFm;
    private MyFragment myFm;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();

    }

    //初始化Fragment
    private void initFragment() {
        fm = getSupportFragmentManager();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        currentFragment = new Fragment();
        homeFm = new HomeFragment(MainAc.this);
        friendFm = new FriendFragment();
        messageFm = new MessageFragment();
        myFm = new MyFragment();
//            }
//        }).start();
        showFragment(homeFm);

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        bottom = (LinearLayout) findViewById(R.id.bottom);
        tab_home = (TextView) findViewById(R.id.home);
        tab_friend = (TextView) findViewById(R.id.friend);
        findViewById(R.id.message).setOnClickListener(this);
        findViewById(R.id.my).setOnClickListener(this);

        tab_friend.setOnClickListener(this);
        tab_home.setOnClickListener(this);


        //设置一个默认显示的Fragment
        //setDefaultFragment();
    }


    private void setDefaultFragment() {
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();

        homeFm = new HomeFragment(MainAc.this);
        toolbar.setTitle("Home");
        transaction.add(R.id.fl_content, homeFm);

        transaction.show(homeFm);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        //transaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.home:
                toolbar.setTitle("Home");
//                if (homeFm == null) {
//                    homeFm = new HomeFragment();
//                }
                //transaction.add(R.id.fl_content,homeFm);
//                if (friendFm != null) {
//                    transaction.hide(friendFm);
//                }
//                transaction.show(homeFm);
                showFragment(homeFm);
                break;
            case R.id.friend:

                toolbar.setTitle("Friend");
//                if (friendFm == null) {
//                    friendFm = new FriendFragment();
//                    transaction.add(R.id.fl_content, friendFm);
//                }
//                transaction.addToBackStack(null);
//                if (homeFm != null) {
//                    transaction.hide(currentFragment);
//                }
//
//                transaction.show(friendFm);
//                if (friendFm==null){
//                    friendFm=new FriendFragment();
//                }
                showFragment(friendFm);
                break;
            case R.id.message:
                toolbar.setTitle("Message");
//                if (messageFm==null){
//                    messageFm=new MessageFragment();
//                }
                showFragment(messageFm);
                break;
            case R.id.my:
                toolbar.setTitle("My");
//                if (myFm==null){
//                    myFm=new MyFragment();
//                }
                showFragment(myFm);
                break;
        }
        //transaction.commit();
    }

    /**
     * 定义一个Fragment基类,用于接受实例化的Fragment,
     * 判断参数是否等于当前fragment,如果不是开启新事务,
     * 把当前fragment隐藏,把实例化参数fragment赋值给当前fragment,
     * 判断参数fragment是否已经添加到事务中,没有就add()并显示show(),有则直接显示
     */
    private void showFragment(Fragment fragment) {
        if (currentFragment != fragment) {
            transaction = fm.beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.fl_content, fragment).show(fragment);
            } else {
                transaction.show(fragment);
            }
            transaction.commit();
        }
    }

    @Override
    public void sendValue(String name, int age) {
        Log.i(TAG, "name:" + name + ",age:" + age);
    }
}
