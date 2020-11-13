package com.artist;

public class Artist {
    private String name = "Default";
    private String company;
    private String artistInfo;

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public String getCompany(){return this.company;}
    public void setCompany(String company){this.company = company;}

    public String getArtistInfo(){return this.artistInfo;}
    public void setArtistInfo(String artistInfo){this.artistInfo = artistInfo;}
}
