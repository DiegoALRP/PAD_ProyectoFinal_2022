package es.ucm.fdi.emtntr.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private String mText;

    public NotificationsViewModel() {
        mText="This is notifications fragment";
    }

    public String getText() {
        return mText;
    }
}