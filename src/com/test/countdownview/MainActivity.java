package com.test.countdownview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private ImageView mIv;
	private Button mBtn;
	
	private CountdownDrawable mCdDrawable;
	private Animator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mIv = (ImageView)findViewById(R.id.iv);
        mCdDrawable = new CountdownDrawable(getResources().getDimensionPixelSize(R.dimen.drawable_ring_size)
				, getResources().getColor(R.color.dark_grey), getResources().getColor(R.color.brightly_grey)
				, getResources().getColor(R.color.holo_green_light), 5, getResources().getColor(R.color.red));
        mIv.setImageDrawable(mCdDrawable);
        
        mBtn = (Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(mBtnOnClickListener);
    }


    private View.OnClickListener mBtnOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(mAnimator != null) {
				mAnimator.cancel();
			}
			mIv.setVisibility(View.VISIBLE);
			mAnimator = prepareAnimator();
			mAnimator.start();
		}
	};
	
	private Animator prepareAnimator() {
		AnimatorSet animation = new AnimatorSet();
		
		// 进度条动画
		ObjectAnimator progressAnimator = ObjectAnimator.ofFloat(mCdDrawable, "progress", 1f, 0f);
		progressAnimator.setDuration(5000);
		progressAnimator.setInterpolator(new LinearInterpolator());
		progressAnimator.addListener(new Animator.AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				mIv.setVisibility(View.GONE);
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				mIv.setVisibility(View.GONE);
			}
		});
		
		// 居中的倒计时数字
		ObjectAnimator showNumberAnimator = ObjectAnimator.ofInt(mCdDrawable, "showNumber", 5, 0);
		showNumberAnimator.setDuration(5000);
		showNumberAnimator.setInterpolator(new LinearInterpolator());
		
		animation.playTogether(progressAnimator, showNumberAnimator);
		return animation;
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
