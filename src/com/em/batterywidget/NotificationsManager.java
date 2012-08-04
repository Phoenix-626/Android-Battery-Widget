/*
*  Copyright 2012 Erkan Molla
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/

package com.em.batterywidget;

import com.em.batterywidget.preferences.Preferences;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


public class NotificationsManager {
	
    private static final int _ID = 123;

    private NotificationManager  notificationManager;
    private Context              mContext;
    
    
    public NotificationsManager(Context context) {
    	
    	notificationManager = (NotificationManager) 
    			mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    	
    	mContext = context;
    }

    @SuppressWarnings("deprecation")
	public void updateNotificationIcon(int batteryLevel, boolean status, Preferences prefSettings) {

    	if(!(prefSettings.getValue(Constants.NOTIFY_ICON_SETTINGS, false)))
    	{
    		notificationManager.cancel(_ID);
    		return;
    	}

    	int icon = status ? R.drawable.ic_stat_charge : R.drawable.ic_stat_battery_level;
        
		Notification notification = new Notification(icon, null, System.currentTimeMillis());
        notification.iconLevel = batteryLevel;
        notification.flags =
            Notification.FLAG_ONGOING_EVENT |
            Notification.FLAG_ONLY_ALERT_ONCE |
            Notification.FLAG_NO_CLEAR | 
            Notification.FLAG_SHOW_LIGHTS;
        
        Intent notificationIntent   = new Intent(mContext, BatteryWidgetActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
        String title                = mContext.getString(R.string.app_name);
        
        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.notification);
        contentView.setImageViewResource(R.id.notificationImage, R.drawable.launcher);
        contentView.setTextViewText(R.id.notificationTitle, title);
        contentView.setTextViewText(R.id.notificationLevel, "Level: " + batteryLevel);
        contentView.setTextViewText(R.id.notificationStatus, "Status: " + batteryLevel);
        
        notification.contentView   = contentView;
        notification.contentIntent = contentIntent;
        notification.tickerText    = null;
        
        notificationManager.notify(_ID, notification);
    }
    
}
