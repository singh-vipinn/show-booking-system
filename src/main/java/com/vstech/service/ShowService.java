package com.vstech.service;

import java.util.List;

public interface ShowService {

    void registerShow(String name, String genre);
    void onboardShowSlots(String name, List<String> slots);
    void showAvailByGenre(String genre);
}
