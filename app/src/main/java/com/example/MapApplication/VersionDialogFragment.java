/**
 *
 */
package com.example.MapApplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v4.app.DialogFragment;

public class VersionDialogFragment extends DialogFragment
implements OnClickListener{

	@Override
	public Dialog onCreateDialog(android.os.Bundle savedInstanceState)
	{
		return new AlertDialog.Builder(getActivity())
		    .setTitle(R.string.action_version)
		    .setMessage(R.string.versionMsg)
		    .setPositiveButton("OK", this)
		    .create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		return;
	}


}
