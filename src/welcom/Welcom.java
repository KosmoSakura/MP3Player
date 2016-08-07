package welcom;

import java.util.ArrayList;
import java.util.List;

import main.MainActivity;

import com.example.project_mp3player.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcom extends Activity {
	ViewPager vpShow;
	SharedPreferences sp;
	Editor ed;
	List<View> listShow;
	ShowAdapter adapter;
	ShowListener lis;
	ImageView[]arr;
	Button btnShow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcom);
			init();
	}
	public void init() {
		vpShow = (ViewPager) findViewById(R.id.vpShow);
		btnShow = (Button) findViewById(R.id.btnShow);
		
		ApplicationInfo info = getApplicationInfo();
		LayoutInflater lay = getLayoutInflater();
		
		arr = new ImageView[4];
		listShow = new ArrayList<View>();
		String[]sarr = {"     该应用是一款轻量级的音乐播放软件","     集成了常用的音乐播放功能：播放、暂停、停止、上一曲、下一曲","     可以展示手机存储卡上的音乐文件列表，可以将该列表作为播放列表","     也可以自定义播放列表，实现个性化定制"};
		
		for (int i = 0; i < 4; i++) {
			int id = getResources().getIdentifier("ivP"+i, "id", info.packageName);
			arr[i]= (ImageView) findViewById(id);
			
			View v = lay.inflate(R.layout.show, null);
			TextView tvShow = (TextView) v.findViewById(R.id.tvShow);
			tvShow.setText(sarr[i]);
			listShow.add(v);
		}
		adapter = new ShowAdapter(listShow);
		vpShow.setAdapter(adapter);
		lis = new ShowListener(btnShow, arr);
		vpShow.setOnPageChangeListener(lis);
		btnShow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				finish();
				startActivity(new Intent(Welcom.this, MainActivity.class));
				
			}
		});
	}
}
