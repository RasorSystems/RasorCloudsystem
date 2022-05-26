package net.cloud.gameserver;

public class WrapperInformations {

    String servername;
    int port;
    String usedWrapper;

    public WrapperInformations(String servername, int port, String usedWrapper){
        this.port = port;
        this.usedWrapper = usedWrapper;
        this.servername = servername;
    }
}
