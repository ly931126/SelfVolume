package com.liye.study.selfvolumecircleprogrssbar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liye.study.selfvolumecircleprogrssbar.util.VolumeController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {
	private static final String	TAG					= MainActivity.class.getSimpleName();
	@BindView(R.id.volume_add_img_btn)
	TextView					mVolumeAddImgBtn;
	@BindView(R.id.volume_reduce_img_btn)
	TextView					mVolumeReduceImgBtn;
	@BindView(R.id.circle_progress_bar)
	VolumeProgressBar			mCircleProgressBar;
	
	private VolumeController	mVolumeController	= null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		mVolumeController = new VolumeController(this);
		initCircleProgressBar();
	}

	private void initCircleProgressBar() {
		mCircleProgressBar.setMax(mVolumeController.getMaxVolume());
		mCircleProgressBar.setCurrentProgress(mVolumeController.getCurrentVolume());
	}

	@OnClick({R.id.volume_add_img_btn, R.id.volume_reduce_img_btn})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.volume_add_img_btn :
				mVolumeController.increaseVolume();
				mCircleProgressBar.setCurrentProgress(mVolumeController.getCurrentVolume());
				Log.e("############ ", mVolumeController.getCurrentVolume() + "");
				break;
			
			case R.id.volume_reduce_img_btn :
				mVolumeController.reduceVolume();
				mCircleProgressBar.setCurrentProgress(mVolumeController.getCurrentVolume());
				break;
		}
	}
}
