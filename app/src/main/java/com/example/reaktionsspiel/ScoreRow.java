package com.example.reaktionsspiel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

public class ScoreRow implements Parcelable {
    public long score;
    public String name;
    public int place;
    public long time;
    public ScoreRow(long s, String n) {  //add one for Time when it was made
        score = s;
        name = n;
        place = 0;  //standart wert für place
        time = System.currentTimeMillis();
    }

    protected ScoreRow(Parcel in) {
        score = in.readLong();
        name = in.readString();
        place = in.readInt();
        time = in.readLong();
    }

    public static final Creator<ScoreRow> CREATOR = new Creator<ScoreRow>() {
        @Override
        public ScoreRow createFromParcel(Parcel in) {
            return new ScoreRow(in);
        }

        @Override
        public ScoreRow[] newArray(int size) {
            return new ScoreRow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(score);
        dest.writeString(name);
        dest.writeInt(place);
        dest.writeLong(time);
    }
}
