package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.project_mp3player.R;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Frag_Video extends Fragment {
	List<HashMap<String, String>> list;
	ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.frag_video, container, false);

		lv = (ListView) v.findViewById(R.id.lvVideo);
		list = new ArrayList<HashMap<String, String>>();
		
		Cursor c = getActivity().getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
				null);
		
		while (c.moveToNext()) {
			String path = c.getString(c
					.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
			String title = c.getString(c
					.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
			long l = c.getLong(c
					.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
			int si = (int) (l / (1024 * 1024));
			String size = si + "Mb";
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", title);
			map.put("size", size);
			map.put("path", path);
			list.add(map);
		}
		c.close();
		TextView t = (TextView) v.findViewById(R.id.ttt);
		t.setText("共找到视频"+list.size()+"个");
		SimpleAdapter adapter = new SimpleAdapter(getActivity(),
				(List<? extends Map<String, ?>>) list, R.layout.adapter,
				new String[] { "title", "size", "path" }, new int[] {
						R.id.tvName, R.id.tvSize, R.id.tvUrl });

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent();
				i.setClass(getActivity(), Media.class);
				HashMap map = (HashMap) list.get(position);
				String u = (String) map.get("path");
				i.putExtra("pos", u);
				startActivity(i);
			}
		});

		return v;
	}
}
