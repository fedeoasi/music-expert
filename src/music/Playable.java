package music;

public interface Playable {
    public void play();
    public void stop();
    public void save();
    public void ottava(boolean isSelected);
    public void loop(boolean isSelected);
    public void ChangeBPM(int bpm);
}
