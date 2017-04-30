package com.liye.study.selfvolumecircleprogrssbar.util;

import android.content.Context;
import android.media.AudioManager;

public class VolumeController {
	
	private static final String	TAG				= VolumeController.class.getSimpleName();
	
	private AudioManager		mAudioManager	= null;
	private int					mCurrentVolume	= 0;
	private int					mMaxVolume		= 0;
	
	public VolumeController(Context context) {
		mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		OpenVolume();
		mCurrentVolume = getCurrentVolume();
	}
	
	public int getCurrentVolume() {
		return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}
	
	public void reduceVolume() {
		mCurrentVolume = getCurrentVolume();
		if (getCurrentVolume() > 0) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, --mCurrentVolume, 0);
		}
	}
	
	public void increaseVolume() {
		mCurrentVolume = getCurrentVolume();
		if (mCurrentVolume < getMaxVolume()) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, ++mCurrentVolume, 0);
		}
	}
	
	private void OpenVolume() {
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}
	
	/**
	 * get max volume
	 */
	public int getMaxVolume() {
		mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		return mMaxVolume;
	}
	
	/**
	 * set default volume
	 */
	private void setDefaultVolume(int currentVolume) {
		if (currentVolume != 0) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
		}
	}
	
	/** set mute */
	private void setMuteOrPlayMute() {
		if (getCurrentVolume() > 0) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
		}
	}
	
	/**
	 * judge isMute
	 */
	public boolean judgeMute() {
		if (getCurrentVolume() > 0) {
			setMuteOrPlayMute();
			return true;
		} else {
			setDefaultVolume(mCurrentVolume);
			return false;
		}
	}
}
