package android.tsinglink.myplugin

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver


private const val ACTION_FOO = "android.tsinglink.myplugin.action.FOO"
private const val ACTION_BAZ = "android.tsinglink.myplugin.action.BAZ"

private const val EXTRA_PARAM1 = "android.tsinglink.myplugin.extra.PARAM1"
private const val EXTRA_PARAM2 = "android.tsinglink.myplugin.extra.PARAM2"


/**
 *   IntentService 与 Service 的区别：
 *
 *   1. Service 是长期运行在后台的应用程序组件，处理耗时任务时，必须要开启一个单独的线程进行处理
 *
 *   2. IntentService 继承于 Service 并在其内部单独封装了一个工作线程来处理耗时操作，启动方式与启动传统 Service 一致。
 *
 *      工作特点：每次启动时会 mServiceHandler.handleMessage(msg) --> onHandleIntent(intent) --> stopSelf(startId)
 *      即：每次启动 IntentService 时， onHandleIntent(intent) 方法都会执行，执行完毕后就即会关闭该 Service
 *         同时由于 Handler 的特性，即使启动 IntentService 多次，每一个任务也会以工作队列的方式依次执行
 */
class TestIntentService : IntentService("TestIntentService") {

	override fun onHandleIntent(intent: Intent?) {
		when (intent?.action) {
			ACTION_FOO -> {
				// Parcelable 类型的数据跨进程传输时，必须重新设置 ClassLoader，这里因为添加了白名单，所以会以宿主的 ClassLoader 加载
				intent.setExtrasClassLoader(this.classLoader)

				val param1 = intent.getStringExtra(EXTRA_PARAM1)
				val param2: ResultReceiver? = intent.getParcelableExtra(EXTRA_PARAM2)
				handleActionFoo(param1, param2)
			}
			ACTION_BAZ -> {
				val param1 = intent.getStringExtra(EXTRA_PARAM1)
				val param2 = intent.getStringExtra(EXTRA_PARAM2)
				handleActionBaz(param1, param2)
			}
		}
	}


	private fun handleActionFoo(param1: String?, resultReceiver: ResultReceiver?) {
		println("handleActionFoo------------> param1 = [${param1}], param2 = [${resultReceiver}]")

		resultReceiver?.send(0, Bundle().apply {
			this.putString(param1, param1)
		})
	}


	private fun handleActionBaz(param1: String?, param2: String?) {
		println("handleActionBaz-------------> param1 = [${param1}], param2 = [${param2}]")
	}

	companion object {

		@JvmStatic
		fun startActionFoo(context: Context, param1: String, param2: String) {
			val intent = Intent(context, TestIntentService::class.java).apply {
				action = ACTION_FOO
				putExtra(EXTRA_PARAM1, param1)
				putExtra(EXTRA_PARAM2, param2)
			}
			context.startService(intent)
		}


		@JvmStatic
		fun startActionBaz(context: Context, param1: String, param2: String) {
			val intent = Intent(context, TestIntentService::class.java).apply {
				action = ACTION_BAZ
				putExtra(EXTRA_PARAM1, param1)
				putExtra(EXTRA_PARAM2, param2)
			}
			context.startService(intent)
		}
	}
}