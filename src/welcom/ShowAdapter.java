package welcom;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ShowAdapter extends PagerAdapter{
	List<View> list;
	
	
	
	public ShowAdapter(List<View> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO �Զ����ɵķ������
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO �Զ����ɵķ������
		return arg0==arg1;
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO �Զ����ɵķ������
		View v = list.get(position);
		container.addView(v);
		return v;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO �Զ����ɵķ������
		View v = list.get(position);
		container.removeView(v);
	}

}
