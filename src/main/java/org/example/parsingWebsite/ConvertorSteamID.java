package org.example.parsingWebsite;


public class ConvertorSteamID {

    /**
     * Конвертирует steamId64 в steamID3
     */
    public String convertSteamID64toSteamID3(String steamID64){
        long steamid64ident = 76561197960265728L;
        String start = "[U:1:";

        // оставим буфер, про потоки еще нужно будет подумать
        StringBuffer stringBuffer = new StringBuffer(start);
        long res = Long.parseLong(steamID64) - steamid64ident;
        stringBuffer.append(res);
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
