package org.techtown.memanage;

import android.os.Parcel;
import android.os.Parcelable;

public class MainToStartData implements Parcelable {
    String mode_;

    public MainToStartData(String mode){
         mode_ = mode;
    }

    public MainToStartData(Parcel src){
        mode_ = src.readString();
    }

    public static final Parcelable.Creator CREATOR= new Parcelable.Creator(){
        public MainToStartData createFromParcel(Parcel in){
            return new MainToStartData(in);
        }

        public MainToStartData[] newArray(int size){
            return new MainToStartData[size];
        }
    };

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(mode_);
    }

}
