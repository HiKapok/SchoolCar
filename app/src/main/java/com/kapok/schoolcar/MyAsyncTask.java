package com.kapok.schoolcar;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.w3c.dom.Text;

/*
    1.异步任务的实例必须在UI线程中创建。
    2.execute(Params... params)方法必须在UI线程中调用。
    3.不要手动调用onPreExecute()，doInBackground(Params... params)，onProgressUpdate(Progress... values)，onPostExecute(Result result)这几个方法。
    4.不能在doInBackground(Params... params)中更改UI组件的信息。
    5.一个任务实例只能执行一次，如果执行第二次将会抛出异常。
 */

public abstract class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
    private ProgressDialog progressDialog;
    private String textString;
    MyAsyncTask(ProgressDialog progressDialog,String textString){
        this.progressDialog=progressDialog;
        this.textString=textString;
    }
    /*
    在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。
    在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。
     */
    @Override
    abstract protected String doInBackground(Integer... params); /*{
        return null;
    }*/
    /*
    在这里操作UI,当后台操作结束时，
    此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。
     */
    @Override
    protected void onPostExecute(String result) {
        //progressDialog.setMessage(textString+"完成");
        progressDialog.dismiss();
    }
    /*
    在execute(Params... params)被调用后立即执行，一般用来在执行后台任务前对UI做一些标记。
     */
    @Override
    protected void onPreExecute() {

        //progressDialog.setMessage("开始"+textString);
    }
    /*
    onProgressUpdate方法用于更新进度信息
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }
    /*
    onCancelled方法用于在取消执行中的任务时更改UI
     */
    @Override
    protected void onCancelled(){
        //progressDialog.setMessage(textString+"被取消");

    }
}
