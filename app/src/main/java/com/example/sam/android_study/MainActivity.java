package com.example.sam.android_study;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private List<View> views = new ArrayList<View>();
	private ViewPager viewPager;
	private LinearLayout llChat, llFriends, llContacts, llSettings;
	private ImageView ivChat, ivFriends, ivContacts, ivSettings, ivCurrent;
	private TextView tvChat, tvFriends, tvContacts, tvSettings, tvCurrent;
	private TextView tvTitle;

	public static String number;//phone number

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();

		initData();
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		llChat = (LinearLayout) findViewById(R.id.llChat);
		llFriends = (LinearLayout) findViewById(R.id.llFriends);
		llContacts = (LinearLayout) findViewById(R.id.llContacts);
		llSettings = (LinearLayout) findViewById(R.id.llSettings);

		llChat.setOnClickListener(this);
		llFriends.setOnClickListener(this);
		llContacts.setOnClickListener(this);
		llSettings.setOnClickListener(this);

		ivChat = (ImageView) findViewById(R.id.ivChat);
		ivFriends = (ImageView) findViewById(R.id.ivFriends);
		ivContacts = (ImageView) findViewById(R.id.ivContacts);
		ivSettings = (ImageView) findViewById(R.id.ivSettings);

		tvChat = (TextView) findViewById(R.id.tvChat);
		tvFriends = (TextView) findViewById(R.id.tvFriends);
		tvContacts = (TextView) findViewById(R.id.tvContacts);
		tvSettings = (TextView) findViewById(R.id.tvSettings);

		tvTitle = (TextView) findViewById(R.id.tvTitle);

		ivChat.setSelected(true);
		tvChat.setSelected(true);
		ivCurrent = ivChat;
		tvCurrent = tvChat;

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				changeTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void initData() {
		LayoutInflater mInflater = LayoutInflater.from(this);
		View tab01 = mInflater.inflate(R.layout.tab01, null);
		View tab02 = mInflater.inflate(R.layout.tab02, null);
		View tab03 = mInflater.inflate(R.layout.tab03, null);
		View tab04 = mInflater.inflate(R.layout.tab04, null);
		views.add(tab01);
		views.add(tab02);
		views.add(tab03);
		views.add(tab04);

		MyPagerAdapter adapter = new MyPagerAdapter(views);
		viewPager.setAdapter(adapter);

		//number
		tvTitle.setText(number);
	}

	@Override
	public void onClick(View v) {
		changeTab(v.getId());
	}

	private void changeTab(int id) {
		ivCurrent.setSelected(false);
		tvCurrent.setSelected(false);
		switch (id) {
		case R.id.llChat:
			viewPager.setCurrentItem(0);
		case 0:
			ivChat.setSelected(true);
			ivCurrent = ivChat;
			tvChat.setSelected(true);
			tvCurrent = tvChat;
			break;
		case R.id.llFriends:
			viewPager.setCurrentItem(1);
		case 1:
			ivFriends.setSelected(true);
			ivCurrent = ivFriends;
			tvFriends.setSelected(true);
			tvCurrent = tvFriends;
			break;
		case R.id.llContacts:
			viewPager.setCurrentItem(2);
		case 2:
			ivContacts.setSelected(true);
			ivCurrent = ivContacts;
			tvContacts.setSelected(true);
			tvCurrent = tvContacts;
			break;
		case R.id.llSettings:
			viewPager.setCurrentItem(3);
		case 3:
			ivSettings.setSelected(true);
			ivCurrent = ivSettings;
			tvSettings.setSelected(true);
			tvCurrent = tvSettings;
			break;
		default:
			break;
		}
	}

	public void logoutAction(View view) {
		number = "";
		finish();
	}
}
