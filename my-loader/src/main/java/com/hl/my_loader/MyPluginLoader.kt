package com.hl.my_loader.loader

import android.content.Context
import com.hl.my_loader.MyComponentManager
import com.tencent.shadow.core.loader.ShadowPluginLoader
import com.tencent.shadow.core.loader.managers.ComponentManager

/**
 * 这里的类名和包名需要固定
 * com.tencent.shadow.sdk.pluginloader.PluginLoaderImpl
 */
class MyPluginLoader(hostAppContext: Context) : ShadowPluginLoader(hostAppContext) {


	private val componentManager: ComponentManager

	init {
		componentManager = MyComponentManager(hostAppContext)
	}

	override fun getComponentManager(): ComponentManager {
		return componentManager
	}
}