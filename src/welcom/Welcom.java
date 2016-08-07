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
		String[]sarr = {"     ��Ӧ����һ�������������ֲ������","     �����˳��õ����ֲ��Ź��ܣ����š���ͣ��ֹͣ����һ������һ��","     ����չʾ�ֻ��洢���ϵ������ļ��б����Խ����б���Ϊ�����б�","     Ҳ�����Զ��岥���б�ʵ�ָ��Ի�����"};
		
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
