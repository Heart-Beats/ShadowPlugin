package com.tencent.shadow.dynamic.loader.impl

import android.content.Context
import com.hl.my_loader.loader.MyPluginLoader
import com.tencent.shadow.core.loader.ShadowPluginLoader

/**
 * 这个类的包名类名是固定的。
 *
 *
 * @see 见com.tencent.shadow.dynamic.loader.impl.DynamicPluginLoader#CORE_LOADER_FACTORY_IMPL_NAME
 */
class CoreLoaderFactoryImpl : CoreLoaderFactory {

	override fun build(hostAppContext: Context): ShadowPluginLoader {
		return MyPluginLoader(hostAppContext)
	}
}