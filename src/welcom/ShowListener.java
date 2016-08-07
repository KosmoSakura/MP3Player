package welcom;

import com.example.project_mp3player.R;
import com.example.project_mp3player.R.drawable;

import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;

public class ShowListener extends ViewPager.SimpleOnPageChangeListener {
	Button b;
	ImageView[] arr;

	public ShowListener(Button b, ImageView[] arr) {
		this.b = b;
		this.arr = arr;
	}
	
	@Override
	public void onPageSelected(int position) {
		// TODO 自动生成的方法存根
		super.onPageSelected(position);
		for (int i = 0; i < 4; i++) {
			if (i==position) {
				arr[position].setImageResource(R.drawable.printred2);
			}else {
				arr[i].setImageResource(R.drawable.printred3);
			}
		}
		if (position==3) {
			b.setText("开始体验");
		}else {
			b.setText("跳过");
		}
	}

}
