package main;

import com.example.project_mp3player.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.VideoView;

public class Media extends Activity {
	VideoView vv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media);
		vv = (VideoView) findViewById(R.id.vv);
		Intent i = getIntent();
		String s = i.getStringExtra("pos");
		Uri uri = Uri.parse(s);
		vv.setVideoURI(uri);
		vv.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.media, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自动生成的方法存根
		System.exit(0);
		return super.onOptionsItemSelected(item);
	}
}
