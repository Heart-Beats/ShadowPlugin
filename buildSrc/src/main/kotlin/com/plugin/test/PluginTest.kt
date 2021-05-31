package com.plugin.test

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.isAccessible

/**
 * @author  张磊  on  2021/05/31 at 14:44
 * Email: 913305160@qq.com
 */
class PluginTest : Plugin<Project> {


	override fun apply(project: Project) {
		System.err.println("ShadowPlugin project.name==" + project.name)

		val baseExtension = getBaseExtension(project)
		val sdkDirectory = baseExtension.sdkDirectory
		val androidJarPath = "platforms/${baseExtension.compileSdkVersion}/android.jar"

		println("测试 Gradle 插件： 当前项目使用的 androidJarPath == $androidJarPath")
	}


	fun getBaseExtension(project: Project): BaseExtension {
		val plugin = project.plugins.getPlugin(AppPlugin::class.java)
		if (com.android.builder.model.Version.ANDROID_GRADLE_PLUGIN_VERSION == "3.0.0") {
			val method = BasePlugin::class.declaredFunctions.first { it.name == "getExtension" }
			method.isAccessible = true
			return method.call(plugin) as BaseExtension
		} else {
			return project.extensions.getByName("android") as BaseExtension
		}


	}
}