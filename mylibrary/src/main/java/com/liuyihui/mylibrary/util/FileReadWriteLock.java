package com.liuyihui.mylibrary.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

/**
 * 实现多线程安全的文件读写锁
 *
 * @author liuyi 2017年11月8日14:38:22
 */
public class FileReadWriteLock {
    private String lockFileName = null;

    private FileChannel channel = null;

    private FileLock lock = null;


    public FileReadWriteLock(String lockFileName) {
        this.lockFileName = lockFileName;
    }

    public static FileReadWriteLock get(String lockFileName) {
        FileReadWriteLock fileReadWriteLock = new FileReadWriteLock(lockFileName);
        return fileReadWriteLock;
    }

    public boolean isLocked() throws FileNotFoundException {

        File file = new File(lockFileName);
        if (!file.exists()) {
            return false;
        }

        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();

        FileLock fileLock = null;

        try {
            fileLock = fileChannel.tryLock();
            return fileLock == null;
        } catch (OverlappingFileLockException e) {
            return true;
        } catch (IOException e) {
            return true;
        } catch (Exception e) {
            return true;
        } finally {
            if (fileLock != null) {
                try {
                    fileLock.release();
                    fileLock = null;

                    if (fileChannel.isOpen()) {
                        fileChannel.close();
                    }
                    fileChannel = null;
                    file = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean obtain() throws IOException {
        File file = new File(lockFileName);
        channel = new RandomAccessFile(file, "rw").getChannel();

        try {
            lock = channel.tryLock();
            return true;
        } catch (OverlappingFileLockException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void unlock() {
        try {
            if (lock != null) {
                lock.release();
            }

            if (channel != null && channel.isOpen()) {
                channel.close();
            }

            lock = null;
            channel = null;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
