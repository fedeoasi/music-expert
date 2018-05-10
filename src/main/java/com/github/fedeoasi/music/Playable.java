package com.github.fedeoasi.music;

public interface Playable {
    void play();

    void stop();

    void save();

    void ottava(boolean isSelected);

    void loop(boolean isSelected);

    void ChangeBPM(int bpm);
}
