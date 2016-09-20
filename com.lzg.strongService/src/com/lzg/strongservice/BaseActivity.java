package com.lzg.strongservice;

import android.app.Activity;
import android.view.View;

/**
 * 
 * @author henry
 *
 */
public class BaseActivity extends Activity {

	/**
	 * 解决强制类型转换的问题
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getViewById(int id) {
		return (T) findViewById(id);
	}

}
