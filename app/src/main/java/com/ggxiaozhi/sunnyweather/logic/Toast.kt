package com.ggxiaozhi.sunnyweather.logic

import android.widget.Toast
import com.ggxiaozhi.sunnyweather.MyApplication
import java.time.Duration

/**
 *
 * @Description:
 * @Author:         ggxz
 * @CreateDate:     2020/6/4 23:23
 * @UpdateUser:
 * @UpdateDate:     2020/6/4 23:23
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApplication.context, this, duration).show()
}

fun String.showToastLong() {
    Toast.makeText(MyApplication.context, this, Toast.LENGTH_LONG).show()
}