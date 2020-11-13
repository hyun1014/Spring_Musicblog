package com.track;

public class Track {
    private String title = "Default";
    private String artist = "Default";
    private String album = "Default";
    private String youtubeId;
    private String lyrics;

    public String getTitle(){return this.title;}
    public void setTitle(String title){this.title = title;}

    public String getArtist(){return this.artist;}
    public void setArtist(String artist){this.artist = artist;}

    public String getAlbum(){return this.album;}
    public void setAlbum(String album){this.album = album;}

    public String getYoutubeId(){return this.youtubeId;}
    public void setYoutubeId(String youtubeId){this.youtubeId = youtubeId;}

    public String getLyrics(){return this.lyrics;}
    public void setLyrics(String lyrics){this.lyrics = lyrics;}
}
