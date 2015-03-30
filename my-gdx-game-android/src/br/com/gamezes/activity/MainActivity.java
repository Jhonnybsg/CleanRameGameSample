package br.com.gamezes.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import br.com.gamezes.abstracts.GameConfiguration;
import br.com.gamezes.listeners.ConfirmInterface;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication implements
		ConfirmInterface {

	public boolean onConfirm = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		cfg.useWakelock = true;

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		initialize(new GameCicle(this), cfg);
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public void confirmExit() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Sair")
						.setMessage("Deseja mesmo sair?")
						.setCancelable(false)
						.setPositiveButton("Sim",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}
								})
						.setNegativeButton("Não",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.cancel();
										onConfirm = false;
										GameConfiguration.nextScreen = -1;
										GameConfiguration.currentScreen = 0;
										GameConfiguration.doTransition = false;
									}
								}).create().show();
			}
		});
	}
}