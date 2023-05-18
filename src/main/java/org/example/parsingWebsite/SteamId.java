package org.example.parsingWebsite;


public class SteamId {

    /**
     * Конвертирует steamId64 в steamID3
     * https://gist.github.com/bcahue/4eae86ae1d10364bb66d
     * @param steamID64
     * @return
     */
    public String convertSteamID64toSteamID3(String steamID64){
        long steamid64ident = 76561197960265728l;
        String start = "[U:1:";
        StringBuffer stringBuffer = new StringBuffer(start);
        long res = Long.parseLong(steamID64) - steamid64ident;
        stringBuffer.append(res);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
