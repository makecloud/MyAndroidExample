#include <android/log.h>
#include "com_jni_FFmpeg.h"

#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>
#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libavfilter/avfilter.h"

JNIEXPORT void JNICALL Java_com_jni_FFmpeg_run(JNIEnv *env, jclass obj) {

    char info[40000] = {0};
    av_register_all();
    AVCodec *c_temp = av_codec_next(NULL);
    while(c_temp != NULL){
       if(c_temp->decode!=NULL){
          sprintf(info,"%s[Dec]",info);
       }else{
          sprintf(info,"%s[Enc]",info);
       }
       switch(c_temp->type){
        case AVMEDIA_TYPE_VIDEO:
          sprintf(info,"%s[Video]",info);
          break;
        case AVMEDIA_TYPE_AUDIO:
          sprintf(info,"%s[Audio]",info);
          break;
        default:
          sprintf(info,"%s[Other]",info);
          break;
       }
       sprintf(info,"%s[%10s]\n",info,c_temp->name);
       c_temp=c_temp->next;
    }
    __android_log_print(ANDROID_LOG_INFO,"myTag","info:\n%s",info);
}