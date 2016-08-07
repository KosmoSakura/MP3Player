package headset;

import main.FragList;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HeadSetReceiver extends BroadcastReceiver{
	Context c;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		c = context;
		if (FragList.p != null) {
			if (FragList.p.isPlaying()) {
				boolean isOn = intent.getIntExtra("state", 0) != 0;
				if (isOn) {
					t("�����Ѳ���");
					FragList.fl.play();
				}else {
					t("�����Ѱγ�");
					FragList.fl.play();
				}
			}
		}
		
	}

	private void t(String s) {
		Toast.makeText(c, s, 0).show();
		
	}

}
