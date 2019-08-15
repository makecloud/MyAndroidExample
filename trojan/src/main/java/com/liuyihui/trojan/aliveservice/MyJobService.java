package com.liuyihui.trojan.aliveservice;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    public MyJobService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        startJobSheduler();
    }

    public void startJobSheduler() {
        try {

            JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobService.class.getName()))
                    .setPeriodic(5)
                    .setPersisted(true)
                    .build();

            JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(jobInfo);

        } catch (Exception ex) {
            Log.e("MyJobService", "", ex);
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
