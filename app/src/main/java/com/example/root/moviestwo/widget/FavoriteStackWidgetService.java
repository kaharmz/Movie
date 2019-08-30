package com.example.root.moviestwo.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavoriteStackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
