package com.liuyihui.playvideodemo.customerView;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public abstract class TotalVideoPlayerListener implements IMediaPlayer.OnBufferingUpdateListener,
        IMediaPlayer.OnCompletionListener, IMediaPlayer.OnPreparedListener,
        IMediaPlayer.OnInfoListener, IMediaPlayer.OnVideoSizeChangedListener,
        IMediaPlayer.OnErrorListener, IMediaPlayer.OnSeekCompleteListener {}
