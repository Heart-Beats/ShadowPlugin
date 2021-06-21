package com.hl.my_loader

import android.content.ComponentName
import android.content.Context
import android.util.Log
import com.hl.my_loader.loader.Constant
import com.tencent.shadow.core.loader.infos.ContainerProviderInfo
import com.tencent.shadow.core.loader.managers.ComponentManager
import java.util.*

class MyComponentManager(private val context: Context) : ComponentManager() {

	companion object {
		private const val TAG = "MyComponentManager"
	}

	/**
	 * 配置插件Activity 到 壳子Activity的对应关系
	 *
	 * @param pluginActivity 插件Activity
	 * @return 壳子Activity
	 */
	override fun onBindContainerActivity(pluginActivity: ComponentName): ComponentName {
		Log.d(TAG, "onBindContainerActivity: pluginActivity==$pluginActivity")
		when (pluginActivity.className) {
		}
		return ComponentName(context, Constant.DEFAULT_ACTIVITY)
	}

	/**
	 * 配置对应宿主中预注册的壳子contentProvider的信息
	 */
	override fun onBindContainerContentProvider(pluginContentProvider: ComponentName): ContainerProviderInfo {
		Log.d(TAG, "onBindContainerContentProvider: pluginContentProvider == $pluginContentProvider")
		return ContainerProviderInfo(
			"com.tencent.shadow.runtime.container.PluginContainerContentProvider",
			Constant.PLUGIN_PROVIDER_AUTHORITY
		)
	}

	override fun getBroadcastInfoList(partKey: String): List<BroadcastInfo> {
		Log.d(TAG, "getBroadcastInfoList: partKey==$partKey")

		//如果有静态广播需要像下面代码这样注册
		//        if (partKey.equals(Constant.PART_KEY_PLUGIN_MAIN_APP)) {
		//            broadcastInfos.add(
		//                    new ComponentManager.BroadcastInfo(
		//                            "com.tencent.shadow.demo.usecases.receiver.MyReceiver",
		//                            new String[]{"com.tencent.test.action"}
		//                    )
		//            );
		//        }
		return ArrayList()
	}
}