
package com.liuyihui.facedetect_quividi_demo;

import com.quividi.jni.*;
import com.quividi.*;

import java.util.List;
import java.util.LinkedList;

import android.app.Activity;
import android.content.Context;

import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.SurfaceView;

import android.graphics.RectF;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.DashPathEffect;

public class DrawingListener extends SurfaceView implements QuividiAPI.Listener {
    private class Person {
        public Person(long ID) {
            this.ID = ID;
            rect = new RectF();
            watching = true;
            gender = GenderType.Unknown;
        }

        public long ID;
        public RectF rect;
        public boolean watching;
        public GenderType gender;
    }

    private List<Person> m_People;

    public DrawingListener(Context context) {
        super(context);
        m_People = new LinkedList<Person>();
    }

    @Override
    public void onWatcher(WatcherNotification msg) {
        Log.i("Quividi", "Received watcher event");
    }

    @Override
    public synchronized void onMotion(MotionNotification msg) {
        Log.i("Quividi", "onMotion");
        Person thePerson = null;
        for (Person person : m_People) {
            if (person.ID == msg.getWatcherID()) {
                thePerson = person;
                break;
            }
        }

        if ((thePerson != null) && (msg.getEnd())) {
            // Left.
            m_People.remove(thePerson);
        } else {
            if (thePerson == null) {
                thePerson = new Person(msg.getWatcherID());
                m_People.add(thePerson);
            }

            thePerson.rect.left = msg.getX() - msg.getWidth() / 2;
            thePerson.rect.top = msg.getY() - msg.getHeight() / 2;
            thePerson.rect.right = msg.getX() + msg.getWidth() / 2;
            thePerson.rect.bottom = msg.getY() + msg.getHeight() / 2;
            thePerson.watching = msg.getWatching();
            thePerson.gender = msg.getGender();
        }

        Activity activity = (Activity) getContext();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    @Override
    public void onOTSCount(OTSCountNotification msg) {
        Log.i("Quividi", "Received OTS count messsage");
    }

    @Override
    public void onGateCount(GateCountNotification msg) {
        Log.i("Quividi", "Received gate count message");
    }

    @Override
    public void onGateSnapshot(GateSnapshotNotification msg) {
        Log.i("Quividi", "Received gate snapshot message");
    }

    @Override
    public void onOverhead(OverheadNotification msg) {
        Log.i("Quividi", "Received overhead event message");
    }

    // Drawing
    @Override
    public synchronized void onDraw(Canvas canvas) {
        Log.i("Quividi", "onDraw");
        playSound();

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setPathEffect(null);

        for (Person person : m_People) {
            if (person.watching)
                paint.setPathEffect(null);
            else
                paint.setPathEffect(new DashPathEffect(new float[]{3, 3}, 0));

            if (person.gender == GenderType.Male)
                paint.setColor(Color.BLUE);
            else if (person.gender == GenderType.Female)
                paint.setColor(Color.RED);
            else
                paint.setColor(Color.WHITE);

            canvas.drawCircle(person.rect.centerX() * getWidth(),
                    (1.0f - person.rect.centerY()) * getHeight(),
                    person.rect.width() / 2 * getWidth(),
                    paint);
        }
    }

    public void playSound() {
        VidiReportsMainActivity.soundPool.play(1, 1, 1, 0, 0, 1);
    }
}
