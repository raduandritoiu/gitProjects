package com.bigct.appint.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.bigct.appint.R;

public class SimpleProgressDialog extends Dialog {

	public SimpleProgressDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawableResource(
				R.drawable.bg_black_transparent);
		setContentView(R.layout.dlg_progress);

	}
}
