package main;

import com.example.project_mp3player.R;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

public class VPListener extends ViewPager.SimpleOnPageChangeListener{
	ImageView[]arr;

	public VPListener(ImageView[] arr) {
		this.arr = arr;
	}
	@Override
	public void onPageSelected(int position) {
		// TODO 自动生成的方法存根
		super.onPageSelected(position);
		for (int i = 0; i < arr.length; i++) {
			if (i == position) {
				arr[i].setImageResource(R.drawable.printdark1);
			}else {
				arr[i].setImageResource(R.drawable.printdark3);
			}
		}
	}
	
}
