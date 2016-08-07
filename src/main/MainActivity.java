package main;

import headset.HeadSetReceiver;

import java.util.ArrayList;
import java.util.List;

import com.example.project_mp3player.R;

import welcom.Welcom;
import android.R.layout;
import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;


public class MainActivity extends FragmentActivity {
	SharedPreferences sp;
	Editor ed;
	List<Fragment> list;
	ViewPager vp;
	ImageView[]arr = new ImageView[4];
	FragmentManager fm;
	VPAdapter vpAdapter;
	int mode=2,i=0,j=0;
	public static MainActivity act;
	public static List<String[]> songList = new ArrayList<String[]>();
	ImageButton ibRe,ibPlay,ibStop;
	public static int cou;
	BroadcastReceiver sr;
	SeekBar sb;
	AudioManager am;
	int vol;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//第一次打开的说明界面
		welcom();
		
		ibRe = (ImageButton) findViewById(R.id.ibRe);
		ibPlay = (ImageButton) findViewById(R.id.ibPlay);
		ibStop = (ImageButton) findViewById(R.id.ibStop);
		
		act =this;
		list = new ArrayList<Fragment>();
		vp = (ViewPager) findViewById(R.id.vp);
		
		//监听耳机插扒
		sr = new HeadSetReceiver();
		IntentFilter fil = new IntentFilter();
		fil.addAction(Intent.ACTION_HEADSET_PLUG);
		registerReceiver(sr, fil);
		
		init();
		
		//获取一个cursor对象
		Cursor c = getMp3Cursor();
		
		while (c.moveToNext()) {
			
			String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
			Long i = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
			int si = (int) (i/(1024*1024));
			String size = si+" Mb";
			String uri = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
			String[]arr = {name,size,uri};
			songList.add(arr);
		}
		c.close();
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//音量
		View v = getLayoutInflater().inflate(R.layout.dialog, null);
		AlertDialog.Builder dia = new AlertDialog.Builder(this);
		dia.setView(v);
		dia.show();
		sb = (SeekBar) v.findViewById(R.id.sb);
		
		am = (AudioManager) this.getSystemService(Service.AUDIO_SERVICE);
		vol = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		sb.setMax(am.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		sb.setProgress(vol);
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO 自动生成的方法存根
				am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
			}
		});
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		unregisterReceiver(sr);
	}
	
	public void myClick(View v){
		
		switch (v.getId()) {
		case R.id.ibStop:
			FragList.fl.stop();
			if (j==0) {
				j=1;i=1;
				ibStop.setImageResource(R.drawable.onstopp);
			}else if (j==1) {
				j=0;i=0;
				ibStop.setImageResource(R.drawable.onstopy);
			}
			break;
		case R.id.ibBack:
			FragList.fl.back();
			break;
		case R.id.ibPlay:
			FragList.fl.play();
			if (i == 0) {
				ibPlay.setImageResource(R.drawable.onplayp);
				i=1;j=1;
			}else if (i==1) {
				i=0;j=0;
				ibPlay.setImageResource(R.drawable.onplayy);
			}
			break;
		case R.id.ibGo:
			FragList.fl.go();
			break;
		case R.id.ibRe:
			if (mode == 0) {
				ibRe.setImageResource(R.drawable.repeat_once);
				tt("单曲循环");
				cou = 1;
				mode = 1;
			}else if (mode == 1) {
				ibRe.setImageResource(R.drawable.repeat_list);
				tt("列表循环");
				mode = 2;
				cou = 2;
			}else if (mode == 2) {
				ibRe.setImageResource(R.drawable.repeat_no);
				tt("随机播放");
				mode = 0;
				cou = 0;
			}
			break;
		}
		
	}

	public void tt(String s){
		Toast.makeText(getApplicationContext(), s, 0).show();
	}
	
	private Cursor getMp3Cursor() {
		ContentResolver cr = this.getContentResolver();
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor = cr.query(uri, null, null, null, null);
		return cursor;
	}



	private void init() {
		ApplicationInfo info = getApplicationInfo();
		
		for(int i=0;i<4;i++){
			int id = getResources().getIdentifier("iv"+i, "id", info.packageName);
			arr[i] = (ImageView) findViewById(id);
		}
		
		FragList fList = new FragList();
		FragInfo fInfo = new FragInfo();
		Fraglrc fLrc = new Fraglrc();
		Frag_Video fVideo = new Frag_Video();
		list.add(fList);
		list.add(fLrc);
		list.add(fInfo);
		list.add(fVideo);
		
		fm = getSupportFragmentManager();
		
		vpAdapter = new VPAdapter(fm, list);
		vp.setAdapter(vpAdapter);
		VPListener lis = new VPListener(arr);
		vp.setOnPageChangeListener(lis);
	}
	
	
	
	private void welcom() {
		sp = getPreferences(MODE_PRIVATE);
		ed = sp.edit();
		boolean b = sp.getBoolean("log", true);
		if (b) {
			ed.putBoolean("log", false);
			ed.commit();
			startActivity(new Intent(MainActivity.this, Welcom.class));
		}
	}

}
