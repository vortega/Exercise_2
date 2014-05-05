package com.vortega.exercise_2.restclient;

import com.vortega.exercise_2.app.ItemDto;
import java.util.List;

/**
 * Created by vortega on 03/05/14.
 */
public interface AsyncTaskListener{
    public void processResponse(String str);
}