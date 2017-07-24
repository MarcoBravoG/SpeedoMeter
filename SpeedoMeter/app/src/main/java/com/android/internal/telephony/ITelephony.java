package com.android.internal.telephony;

/**
 * Created by suneet on 24/7/17.
 */

public interface ITelephony {
    boolean endCall();
    void answerRingingCall();
    void silenceRinger();
}
