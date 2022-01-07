package org.techtown.memanage;

import java.util.ArrayList;
import java.util.List;

public class TabataData {
    // data from db
    private int time_per_session_ = 1; // 20 seconds as default
    private int iterate_goal = 2; //default value, repeat 8 times
    private int time_between_session = 2; // resting time
    private int music_id = 0;

    public String getMode_name() {
        return mode_name;
    }

    private String mode_name = "DEFAULT";


    public TabataData(String mode_name) {
        this.mode_name = mode_name;
    }

    public TabataData(int time_per_session_, int iterate_goal, int time_between_session, int music_id, String mode_name) {
        this.time_per_session_ = time_per_session_;
        this.iterate_goal = iterate_goal;
        this.time_between_session = time_between_session;
        this.music_id = music_id;
        this.mode_name = mode_name;
    }

    public TabataData() {

    }


    public int getMusic_id() {
        return music_id;
    }

    public int getTime_per_session_() {
        return time_per_session_;
    }

    public int getTime_between_session() {
        return time_between_session;
    }

    public int getIterate_goal() {
        return iterate_goal;
    }


}
