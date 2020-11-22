package com.artist;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Artist {
    private String name = "Default";
    private String company;
    private String artistInfo;
    private String author;

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public String getCompany(){return this.company;}
    public void setCompany(String company){this.company = company;}

    public String getArtistInfo(){return this.artistInfo;}
    public void setArtistInfo(String artistInfo){this.artistInfo = artistInfo;}

    public String getAuthor(){return this.author;}
    public void setAuthor(String author){this.author = author;}
}
