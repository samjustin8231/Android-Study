package com.example.sam.android_study.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sam.android_study.R;
import com.example.sam.android_study.adapter.GroupAdapter;
import com.example.sam.android_study.adapter.MyPagerAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends Activity implements OnClickListener {
	//UI controls about view pager and bottom buttons
	private List<View> views = new ArrayList<View>();
	private ViewPager viewPager;
	private LinearLayout llChat, llFriends, llContacts, llSettings;
	private ImageView ivChat, ivFriends, ivContacts, ivSettings, ivCurrent;
	private TextView tvChat, tvFriends, tvContacts, tvSettings, tvCurrent;
	private TextView tvTitle;

	public static String number;//phone number

	//page3 UI controls
	private PullToRefreshListView mPullToRefreshListView;
	private LinkedList<String> mListItems;
	private GroupAdapter mAdapter;
	private String[] mStrings = { "John", "Michelle", "Amy", "Kim", "Mary",
			"David", "Sunny", "James", "Maria", "Michael", "Sarah", "Robert",
			"Lily", "William", "Jessica", "Paul", "Crystal", "Peter",
			"Jennifer", "George", "Rachel", "Thomas", "Lisa", "Daniel", "Elizabeth",
			"Kevin" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();

		initData();
	}

	private void initView() {
		//init view about viewpager and bottom buttons;
		initViewFrame();


	}

	/**
	 * init UI controls(pull to refresh list view) in page3
	 */
	private void initViewPage3() {
		// Set a listener to be invoked when the list should be refreshed.
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				new GetDataTask().execute();
			}
		});
//		mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//				System.out.println("group item click.");
//				ChatActivity.title = mListItems.get(i);
//				Intent intent = new Intent(MainActivity.this, ChatActivity.class);
//				startActivity(intent);
//			}
//		});

		ListView actualListView = mPullToRefreshListView.getRefreshableView();

		mListItems = new LinkedList<String>();
		mListItems.addAll(Arrays.asList(mStrings));

		mAdapter = new GroupAdapter(this,mListItems);
		actualListView.setAdapter(mAdapter);
	}

	/**
	 * init view about viewpager and bottom buttons;
	 */
	private void initViewFrame() {
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
		View tab01 = mInflater.inflate(R.layout.tab_call, null);
		View tab02 = mInflater.inflate(R.layout.tab_call_history, null);
		View tab03 = mInflater.inflate(R.layout.tab_chat, null);
		View tab04 = mInflater.inflate(R.layout.tab_me, null);
		views.add(tab01);
		views.add(tab02);
		views.add(tab03);
		views.add(tab04);

		MyPagerAdapter adapter = new MyPagerAdapter(views);
		viewPager.setAdapter(adapter);

		//number
		tvTitle.setText(number);
	}

	/**
	 * click bottom button
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		changeTab(v.getId());
	}

	/**
	 * switch tab
	 * @param id
	 */
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

			//init UI controls in this page
			initViewPage3();

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
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("注销")
				.setMessage("确定注销吗？")
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						number = "";
						finish();
					}
				})
				.create();
		dialog.show();

	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}
		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addFirst("Added after refresh...");
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullToRefreshListView.onRefreshComplete();
			super.onPostExecute(result);
		}
	}
}
