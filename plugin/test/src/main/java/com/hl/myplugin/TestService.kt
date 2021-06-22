package com.hl.myplugin

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.hl.pluginlib.Person
import com.hl.pluginlib.PluginAidlInterface
import com.hl.pluginlib.PluginAidlListener

class TestService : Service() {

	private companion object {
		private const val TAG = "TestService"
	}

	private var pluginAidlListener: PluginAidlListener? = null

	/**
	 * 1.如果 service 没被创建过，调用 startService() 后会执行 onCreate() 回调；
	 * 2.如果 service 已处于运行中，调用 startService() 不会执行 onCreate() 方法。
	 */
	override fun onCreate() {
		Log.d(TAG, "onCreate: ")
		super.onCreate()
	}

	/**
	 * onBind() 方法是抽象方法，Service 类本身就是抽象类，因此 onBind() 方法必须重写，即使用不到。
	 */
	override fun onBind(intent: Intent): IBinder? {
		Log.d(TAG, "onBind: 传输数据== ${intent.getStringExtra("测试数据")}")
		return object : PluginAidlInterface.Stub() {

			override fun openActivity(activityClassName: String, extras: Bundle?) {
				serviceOpenActivity(activityClassName, extras)
				pluginAidlListener?.onOpenActivity(true, activityClassName)
			}

			override fun setPluginListener(PluginAidlListener: PluginAidlListener?) {
				serviceSetPluginListener(PluginAidlListener)

				pluginAidlListener?.onTransPerson(Person("测试AIDL", 0, "未知"))
			}
		}
	}

	private fun serviceSetPluginListener(pluginAidlListener: PluginAidlListener?) {
		this.pluginAidlListener = pluginAidlListener
	}

	private fun serviceOpenActivity(activity: String, extras: Bundle?) {
		this.startActivity(Intent(this, Class.forName(activity)).also {
			it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
			println("设置 Flags ------------------------- Intent.FLAG_ACTIVITY_NEW_TASK")
			it.replaceExtras(extras)
		})
	}

	override fun onUnbind(intent: Intent?): Boolean {
		Log.d(TAG, "onUnbind: 传输数据== ${intent?.getStringExtra("测试数据")}")
		return super.onUnbind(intent)
	}

	override fun onStart(intent: Intent?, startId: Int) {
		Log.d(TAG, "onStart: 传输数据== ${intent?.getStringExtra("测试数据")}, startId == $startId")
		super.onStart(intent, startId)
	}

	/**
	 *  使用 Context 的 startService() 方法启动 Service 时，该方法就会得到调用。
	 *      onStartCommand()方法很重要，可以在该方法中根据传入的Intent参数进行实际的操作，比如创建一个线程用于下载数据或播放音乐等
	 *
	 *
	 * 该方法的返回值为 Service 的标志位：
	 *
	 *  @see Service.START_STICKY_COMPATIBILITY START_STICKY的兼容版本，但不保证服务被kill后一定能重启
	 *
	 *  @see Service.START_STICKY 粘性的，如果 service 进程被 kill 掉，保留 service 的状态为开始状态，但不保留传送的 intent 对象。随后系统会尝试重新创建 service
	 *  ，创建后即会重新调用 onStartCommand(Intent,int,int) 方法。如果在此期间没有任何启动命令被传递到 service，那么参数 Intent 将为null
	 *
	 *  @see Service.START_NOT_STICKY 非粘性的，在执行完 onStartCommand 后，服务被异常 kill 掉，系统不会自动重启该服务
	 *
	 *  @see Service.START_REDELIVER_INTENT 重传 Intent，在执行完 onStartCommand 后，服务被异常 kill 掉，系统会自动重启该服务，并将 Intent 的值传入
	 *
	 */
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

		Log.d(TAG, "onStartCommand: 传输数据== ${intent?.getStringExtra("测试数据")}, flags == $flags, startId == $startId")
		return super.onStartCommand(intent, flags, startId)
	}

	/**
	 * 在销毁的时候会执行 Service 该方法。
	 *
	 *  1. 通过 startService 启动的 Service 会一直无限运行，只有外部调用了 stopService() 或 stopSelf()方法时，该Service才会停止运行并销毁。
	 *      特点： 一旦服务开启跟调用者(开启者)没有任何关系，开启者退出或挂了，服务都会在后台长期的运行
	 *
	 *      启动的Service的生命周期如下：
	 *          onCreate()---> onStartCommand()（onStart()方法已过时） ---> onDestroy()
	 *
	 *  2. 通过 bindService 启动的 Service：
	 *
	 *      特点：①. 启动的服务和调用者之间是典型的 client-server 模式，调用者是 client，Service 则是 server 端。
	 *              Service 只有一个，但绑定到 Service 上面的 client 可以有多个。这里所的 client 指的是组件，比如某个 Activity。
	 *
	 *           ②. client 可以通过 IBinder 接口获取 Service 实例，从而实现在 client 端直接调用 Service 中的方法以实现灵活交互，
	 *              通过 startService 方法启动是无法实现该效果的。
	 *
	 *           ③.启动的 Service 的生命周期与其绑定的 client 息息相关。当 client 销毁时，client 会自动与 Service 解除绑定。
	 *              当然，client 也可以明确调用 Context 的 unbindService() 方法与 Service 解除绑定。
	 *              当没有任何 client 与 Service 绑定时，Service 会自行销毁。
	 *
	 *      启动的Service的生命周期如下：
	 *          onCreate() --->onBind()--->onUnbind()--->onDestroy()
	 */
	override fun onDestroy() {
		Log.d(TAG, "onDestroy: ")
		super.onDestroy()
	}
}