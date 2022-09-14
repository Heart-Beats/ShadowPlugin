package com.hl.my_runtime;


import android.annotation.SuppressLint;

import com.tencent.shadow.core.runtime.container.PluginContainerActivity;

@SuppressLint("Registered")//无需注册在这个模块的Manifest中，要注册在宿主的Manifest中。
public class PluginDefaultProxyActivity extends PluginContainerActivity {
}
