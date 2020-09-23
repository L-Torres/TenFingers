package com.hemaapp.hm_FrameWork;

import android.content.Context;
import android.widget.Toast;

import com.hemaapp.HemaConfig;
import com.hemaapp.hm_FrameWork.newnet.BaseNetTask;
import com.hemaapp.hm_FrameWork.newnet.BaseNetWorkerNew;
import com.hemaapp.hm_FrameWork.newnet.LoginArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaArrayResult;
import com.hemaapp.hm_FrameWork.result.HemaBaseResult;

import java.util.ArrayList;
import java.util.Iterator;

import xtom.frame.XtomActivityManager;
import xtom.frame.XtomObject;
import xtom.frame.util.XtomSharedPreferencesUtil;

public abstract class HemaNetTaskExecuteListener extends XtomObject implements
        BaseNetWorkerNew.OnTaskExecuteListener {
    public Context mContext;
    private ArrayList<HemaNetTask> failedTasks;// token失效任务队列

    public HemaNetTaskExecuteListener(Context context) {
        mContext = context;
    }

    @Override
    public void onPreExecute(BaseNetWorkerNew netWorker, BaseNetTask task) {
        onPreExecute((HemaNetWorker) netWorker, (HemaNetTask) task);
    }

    @Override
    public void onPostExecute(BaseNetWorkerNew netWorker, BaseNetTask task) {
        onPostExecute((HemaNetWorker) netWorker, (HemaNetTask) task);
    }

    @Override
    public void onExecuteFailed(BaseNetWorkerNew netWorker, BaseNetTask netTask,
                                int failedType) {
        if (HemaConfig.TOAST_NET_ENABLE)
            switch (failedType) {
                case BaseNetWorkerNew.FAILED_DATAPARSE:
//                    Toast.makeText(mContext, R.string.msg_data, Toast.LENGTH_SHORT)
//                            .show();
                    break;
                case BaseNetWorkerNew.FAILED_HTTP:
//                    Toast.makeText(mContext, R.string.msg_http, Toast.LENGTH_SHORT)
//                            .show();
                    break;
                case BaseNetWorkerNew.FAILED_NONETWORK:
                    Toast.makeText(mContext, R.string.msg_nonet, Toast.LENGTH_SHORT)
                            .show();
                    break;
                default:
                    break;
            }

        int taskId = netTask.getId();
        if (taskId == HemaConfig.ID_LOGIN || taskId == HemaConfig.ID_THIRDSAVE) {// 登录任务
            if (failedTasks != null && failedTasks.size() > 0) {// token失效的自动登录，所有任务执行失败
                for (HemaNetTask failedTask : failedTasks) {
                    if (!onAutoLoginFailed((HemaNetWorker) netWorker,
                            failedTask, failedType, null))
                        onExecuteFailed((HemaNetWorker) netWorker,
                                (HemaNetTask) netTask, failedType);
                }
                failedTasks.clear();
            } else {
                onExecuteFailed((HemaNetWorker) netWorker,
                        (HemaNetTask) netTask, failedType);
            }
        } else {
            onExecuteFailed((HemaNetWorker) netWorker, (HemaNetTask) netTask,
                    failedType);
        }

    }
    /**
     * 去除集合重复的数据
     * @param list
     * @return
     */
    public   static   ArrayList  removeDuplicate(ArrayList list)  {
        for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {
            for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {
                if  (list.get(j).equals(list.get(i)))  {
                    list.remove(j);
                }
            }
        }
        return list;
    }
    @Override
    public void onExecuteSuccess(BaseNetWorkerNew worker, BaseNetTask task,
                                 Object result) {
        HemaBaseResult baseResult = (HemaBaseResult) result;
        HemaNetTask netTask = (HemaNetTask) task;
        HemaNetWorker netWorker = (HemaNetWorker) worker;

        if ("200".equals(baseResult.getCode()) && isNull(baseResult.getError_message()) && baseResult.getError_code() != 401) {// 服务器处理成功
            int taskId = netTask.getId();
            if (taskId == HemaConfig.ID_LOGIN
                    || taskId == HemaConfig.ID_THIRDSAVE  || taskId==HemaConfig.ID_FASTLOGIN) {// 如果为登录接口，保存用户信息

                HemaArrayResult<HemaUser> uResult = (HemaArrayResult)baseResult;
                HemaUser user = (HemaUser)uResult.getObjects().get(0);
                String token = user.getToken();
                user.setToken(token);
                if (this.failedTasks != null && this.failedTasks.size() > 0) {
                    if(failedTasks.size()>1){
                        failedTasks=removeDuplicate(failedTasks);
                    }
                    Iterator var12 = this.failedTasks.iterator();
                    while(var12.hasNext()) {
                        HemaNetTask failedTask = (HemaNetTask)var12.next();
                        failedTask.getParams().put("token", token);
                        netWorker.executeTask(failedTask);
                    }
                    this.failedTasks.clear();
                    this.checkUpdate(user);
                    return;
                }
            }
            onServerSuccess(netWorker, netTask, baseResult);
        } else {// 服务器处理失败  token 失效401
//            if (baseResult.getError_code() == 401) {// token失效自动登录，并重新执行该任务
//                if (failedTasks == null)
//                    failedTasks = new ArrayList<HemaNetTask>();
//                failedTasks.add(netTask);
//                if (failedTasks.size() <= 6) {// 确保token失效登录只执行一次
//                    if (!netWorker.thirdSave())// 如果不是第三方登录则调用框架自身登录方法
//
//                        netWorker.clientLogin();
//                }
//            } else {
//                int taskId = netTask.getId();
//                if (taskId == HemaConfig.ID_LOGIN
//                        || taskId == HemaConfig.ID_THIRDSAVE) {// 登录任务
//                    if (failedTasks != null && failedTasks.size() > 0) {// token失效的自动登录，所有任务执行失败
//                        for (HemaNetTask failedTask : failedTasks) {
//                            if (!onAutoLoginFailed((HemaNetWorker) netWorker,
//                                    failedTask, 0, baseResult))
//                                onServerFailed(netWorker, netTask, baseResult);
//                        }
//                        failedTasks.clear();
//                    } else {
//                        onServerFailed(netWorker, netTask, baseResult);
//                    }
//                } else {
                    onServerFailed(netWorker, netTask, baseResult);
//                }
//            }

        }
    }

    private void checkUpdate(HemaUser user) {
        String sysVersion = user.getAndroid_last_version();
        String version = HemaUtil.getAppVersionForSever(mContext);
        if (HemaUtil.isNeedUpDate(version, sysVersion)) {
            new HemaUpGrade(user).alert(version, sysVersion);
        }
    }

    /**
     * Runs on the UI thread before the task run.
     *
     * @param netWorker
     * @param netTask
     */
    public abstract void onPreExecute(HemaNetWorker netWorker,
                                      HemaNetTask netTask);

    /**
     * Runs on the UI thread after the task run.
     *
     * @param netWorker
     * @param netTask
     */
    public abstract void onPostExecute(HemaNetWorker netWorker,
                                       HemaNetTask netTask);

    /**
     * 服务器处理成功
     *
     * @param netWorker
     * @param netTask
     * @param baseResult
     */
    public abstract void onServerSuccess(HemaNetWorker netWorker,
                                         HemaNetTask netTask, HemaBaseResult baseResult);

    /**
     * 服务器处理失败
     *
     * @param netWorker
     * @param netTask
     * @param baseResult
     */
    public abstract void onServerFailed(HemaNetWorker netWorker,
                                        HemaNetTask netTask, HemaBaseResult baseResult);

    /**
     * Runs on the UI thread when the task run failed.
     *
     * @param netWorker
     * @param netTask
     * @param failedType the type of cause the task failed.
     *                   <p>
     *                   See {@link BaseNetWorkerNew#FAILED_DATAPARSE
     *                   BaseNetWorkerNew.FAILED_DATAPARSE},
     *                   {@link BaseNetWorkerNew#FAILED_HTTP BaseNetWorkerNew.FAILED_HTTP},
     *                   {@link BaseNetWorkerNew#FAILED_NONETWORK
     *                   BaseNetWorkerNew.FAILED_NONETWORK}
     *                   </p>
     */
    public abstract void onExecuteFailed(HemaNetWorker netWorker,
                                         HemaNetTask netTask, int failedType);

    /**
     * 自动登录失败
     *
     * @param netWorker
     * @param netTask
     * @param failedType 如果failedType为0表示服务器处理失败,其余参照
     *                   {@link BaseNetWorkerNew#FAILED_DATAPARSE
     *                   BaseNetWorkerNew.FAILED_DATAPARSE},
     *                   {@link BaseNetWorkerNew#FAILED_HTTP BaseNetWorkerNew.FAILED_HTTP},
     *                   {@link BaseNetWorkerNew#FAILED_NONETWORK
     *                   BaseNetWorkerNew.FAILED_NONETWORK}
     * @param baseResult 执行结果(仅当failedType为0时有值,其余为null)
     * @return true表示拦截该任务执行流程, 不会继续调用onExecuteFailed或者onServerFailed方法; false反之
     */
    public abstract boolean onAutoLoginFailed(HemaNetWorker netWorker,
                                              HemaNetTask netTask, int failedType, HemaBaseResult baseResult);
}
