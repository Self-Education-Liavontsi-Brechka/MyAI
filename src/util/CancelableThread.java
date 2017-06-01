package util;

public class CancelableThread extends Thread {
    private volatile boolean isCanceled;

    public CancelableThread() {
    }

    public CancelableThread(Runnable runnable) {
        super(runnable);
    }

    public static boolean currIsCanceled() {
        return Thread.currentThread() instanceof CancelableThread &&
                ((CancelableThread) Thread.currentThread()).isCanceled;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void cancel() {
        isCanceled = true;
    }
}
