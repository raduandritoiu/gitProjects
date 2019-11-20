package com.bigct.appint.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bigct.appint.service.AppMonitorService;

public class BootstrapReceiver extends BroadcastReceiver
{
    public BootstrapReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("Service", "----------BootstrapReceiver----------");
        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            context.startService(new Intent(context, AppMonitorService.class));
        }
        return;
    }
}
