package com.vortega.exercise_2.restclient;

import com.vortega.exercise_2.app.ItemDto;

import java.util.List;

/**
 * Created by vortega on 04/05/14.
 */
public interface MlApiCallbacks{
    public void searchItemsDone(List<ItemDto> items);
    public void getItemDone(ItemDto items);
}
