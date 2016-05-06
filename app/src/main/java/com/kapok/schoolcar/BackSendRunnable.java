package com.kapok.schoolcar;

import android.app.ProgressDialog;
import android.content.Context;

public class BackSendRunnable implements Runnable {
    private Context context;
    private String string;
    public static ProgressDialog pd;
    private MyTask success;
    private MyTask failed;
    private boolean show;
    BackSendRunnable(boolean show,String main,String sub,Context context,String string,MyTask success,MyTask failed){
        this.context=context;
        this.string=string;
        this.success=success;
        this.failed=failed;
        this.show=show;
        if(show) {
            this.pd = ProgressDialog.show(context, main, sub, true, false);
        }
    }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        StrongSocketUtils m_socket = new StrongSocketUtils(context,show);
        if(m_socket.socket==null){
            if(this.pd.isShowing()&&show)
                this.pd.dismiss();
            failed.task(null);
        }else{
           if(m_socket.sendMessage(string)) {
                String tempS = new String("");
                tempS = m_socket.readInternet();
                if(tempS!=null){
                    success.task(tempS);
                } else {
                    failed.task(null);
                }
            }
            m_socket.closeSocket();
            if(this.pd.isShowing()&&show) {
                this.pd.dismiss();
            }
        }
    }
}
