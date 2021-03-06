package com.grafian.quran;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.grafian.quran.R;
import com.grafian.quran.model.Paging;
import com.grafian.quran.model.MetaData.Sura;

public class SuraFragment extends ListFragment {

	private App mApp;
	private SuraAdapter mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mApp = (App) getActivity().getApplication();

		mAdapter = new SuraAdapter();
		setListAdapter(mAdapter);
		getListView().setFastScrollEnabled(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), ViewerActivity.class);
		intent.putExtra(QuranFragment.PAGING, Paging.SURA);
		intent.putExtra(QuranFragment.SURA, position + 1);
		intent.putExtra(QuranFragment.AYA, 1);
		startActivity(intent);
	}

	private static class SuraRowHolder {
		public TextView suraNumber;
		public TextView suraName;
		public TextView suraNameArabic;
	}

	class SuraAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mApp.metaData.getSuraCount();
		}

		@Override
		public Object getItem(int position) {
			return mApp.metaData.getSura(position + 1);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SuraRowHolder holder;
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.main_sura_row, null);

				holder = new SuraRowHolder();
				holder.suraNumber = (TextView) convertView.findViewById(R.id.sura_number);
				holder.suraName = (TextView) convertView.findViewById(R.id.sura_name);
				holder.suraNameArabic = (TextView) convertView.findViewById(R.id.sura_name_arabic);
				convertView.setTag(holder);
			} else {
				holder = (SuraRowHolder) convertView.getTag();
			}

			Sura sura = (Sura) getItem(position);

			String arabic = sura.name;
			holder.suraNumber.setText("" + sura.index + ".");
			holder.suraName.setText(App.getSuraName(sura.index));
			holder.suraNameArabic.setText(arabic);
			holder.suraNameArabic.setTextSize(TypedValue.COMPLEX_UNIT_SP, mApp.config.fontSizeArabic);

			return convertView;
		}
	}

}
