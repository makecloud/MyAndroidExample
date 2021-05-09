package com.oohlink.pituregraphic.glidedemo;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * app模块如果不定一个注解类，glide会提示以下：
 * <p>
 * W/Glide: Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on
 * com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation
 * or LibraryGlideModules will be silently ignored
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

}
