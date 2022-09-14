package com.hl.my_loader.loader

/**
 * @Author  张磊  on  2021/04/09 at 10:21
 * Email: 913305160@qq.com
 */
object Constant {
    /**
     * runtime 模块中定义的壳子Activity, 路径类名保持一致，需要在宿主AndroidManifest.xml注册
     */
    const val DEFAULT_ACTIVITY = "com.hl.my_runtime.PluginDefaultProxyActivity"
    const val SINGLE_INSTANCE_ACTIVITY = "com.hl.my_runtime.PluginSingleInstanceProxyActivity"
    const val SINGLE_TASK_ACTIVITY = "com.hl.my_runtime.PluginSingleTaskProxyActivity"


    // const val PLUGIN_PROVIDER_AUTHORITY = "${BuildConfig.HOST_APP_APPLICATION_ID}.contentprovider.authority.dynamic"
}