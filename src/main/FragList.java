package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.project_mp3player.R;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FragList extends Fragment {
	ListView lv;
	SimpleAdapter adapter;
	public static MediaPlayer p;
	List<String[]> songlist = MainActivity.songList;
	List<HashMap<String, String>> list;
	int index;
	public static FragList fl;
	boolean isPause = false;
	TextView tv;
	String songName;
	int cou = MainActivity.cou;

	public void go() {
		if (cou == 1) {
			p.setLooping(true);
		} else if (cou == 2) {
			index++;
		} else if (cou == 0) {
			index = new Random().nextInt(songlist.size());
		}
		
		
		if (index == list.size()) {
			index =0;
		}
		HashMap<String, String> map = list.get(index);
		Uri uri = Uri.parse(map.get("uri"));
		if (p != null) {
			p.stop();
			p = MediaPlayer.create(MainActivity.act, uri);
			p.start();
		} else {
			p = MediaPlayer.create(MainActivity.act, uri);
			p.start();
		}
	}

	public void back() {
		if (cou == 1) {
			p.setLooping(true);
		} else if (cou == 2) {
			index--;
		} else if (cou == 0) {
			index = new Random().nextInt(songlist.size());
		}
		if (index <0) {
			index = list.size()-1;
		}
		HashMap<String, String> map = list.get(index);
		Uri uri = Uri.parse(map.get("uri"));
		if (p != null) {
			p.stop();
			p = MediaPlayer.create(MainActivity.act, uri);
			p.start();
		} else {
			p = MediaPlayer.create(MainActivity.act, uri);
			p.start();
		}
	}

	public void stop() {
		if (p != null) {
			p.stop();
		}
	}

	public void play() {
		if (p != null) {
			if (isPause == true) {
				p.start();
				isPause = false;
			}
			else if (isPause == false) {
				p.pause();
				isPause = true;
			}
		} else {
			MainActivity.act.tt("该文件不存在");
		}
		tv.setText(songName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.frag_list, container, false);
		ImageButton flash = (ImageButton) v.findViewById(R.id.ibFlash);
		tv = (TextView) v.findViewById(R.id.tvTitle);
		lv = (ListView) v.findViewById(R.id.lv);

		tv.setText("共扫描到"+songlist.size()+"首歌");
		fl = this;

		getAdapter();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				index = position;
				HashMap<String, String> map = list.get(position);
				songName = map.get("name");
				Uri uri = Uri.parse(map.get("uri"));
				if (p != null) {
					p.stop();
					p = MediaPlayer.create(MainActivity.act, uri);
					p.setLooping(true);
					p.start();
				} else {
					p = MediaPlayer.create(MainActivity.act, uri);
					p.setLooping(true);
					p.start();
				}

			}
		});

		flash.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.notifyDataSetChanged();
			}
		});

		return v;
	}

	private void getAdapter() {
		list = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < songlist.size(); i++) {
			String[] arr = songlist.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", arr[0]);
			map.put("size", arr[1]);
			map.put("uri", arr[2]);
			list.add(map);
		}
		adapter = new SimpleAdapter(MainActivity.act,
				(List<? extends Map<String, ?>>) list, R.layout.adapter,
				new String[] { "name", "size", "uri" }, new int[] {
						R.id.tvName, R.id.tvSize, R.id.tvUrl });
		lv.setAdapter(adapter);
	}

}
