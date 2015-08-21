package com.uit.Providers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

public class Database {
	private Context context;

	public Database(Context _context) {
		this.context = _context;
	}

	/**
	 * 
	 */
	public void OpenDatabase() {
		try {
			String destPath = "/data/data/" + context.getPackageName()
					+ "/databases/lythuyetlaixe";
			File f = new File(destPath);
			if (!f.exists()) {
				CopyDB(context.getAssets().open("lythuyetlaixe"),
						new FileOutputStream(destPath));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CopyDB(InputStream inputStream, OutputStream outputStream)
			throws IOException {
		// copy 1k bytes at a time
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, length);
		}
		inputStream.close();
		outputStream.close();
	}
}
