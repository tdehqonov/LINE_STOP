package com.example.DY_PDT;

public  class Constants {
//    public static String URL="http://dy.uzautomotors.com/api/login";
    public static String URL="http://dy.uzautomotors.com/api/login";
    public static String URLSEND_DATA="http://dy.uzautomotors.com/api/send_data";
   // http://b-avto-tashish.uzautomotors.com/api/login
    public static String URL_AUTOINFO_LOGIN="https://b-avto-info.uzautomotors.com/oauth/token";

     /// WEB
    public static String URL_PRODACTIONWEB_LOGIN="https://dynew.uzautomotors.com/api/login";
    public static String URL_PRODACTIONWEB_SEND_DATA="https://t-avto-tashish.uzautomotors.com/api/send_data";


    /// Vagon yuklash
    public static String URL_VAGONGAYUKLASH_LOGIN="http://b-avto-tashish.uzautomotors.com/api/login";
    public static String URL_YOL="https://t-avto-tashish.uzautomotors.com/api/send-vagons";
    public static String URL_VAGON_NOMER="https://t-avto-tashish.uzautomotors.com/api/vagon-number";
    public static String URL_VAGONGA_YUKLASH="https://t-avto-tashish.uzautomotors.com/api/vagon-vins";


//    /// WEB
//    public static String URL_PRODACTIONWEB_LOGIN="http://dy.uzautomotors.com/api/login";
//    public static String URL_PRODACTIONWEB_SEND_DATA="http://b-avto-tashish.uzautomotors.com/api/send_data";
//
        // OFLINE/ ONINE

    public static String URL_ONLINE="https://t-avto-tashish.uzautomotors.com/api/shipping-vehicles";
//    public static String URL_MobileInventarizatsiya="http://t-avto-tashish.uzautomotors.com/api/show-dilers";

//eskisi
// public static String URL_ONLINE="http://b-avto-tashish.uzautomotors.com/api/shipping-vehicles";
//
// public static String URL_MobileInventarizatsiya="http://b-avto-tashish.uzautomotors.com/api/show-dilers";

 //public static String URL_AUTOINFO_WEREHOUSE="https://b-avto-info.uzautomotors.com/api/warehouses";
    public static String URL_AUTOINFO_YUBORISH="https://b-avto-info.uzautomotors.com/api/android";


    public static String URLGET_INFO="http://dy.uzautomotors.com/api/getinfo";
    public static String URL_GETAPK="http://dy.uzautomotors.com/api/getapk";

    public static String TESTURLSEND_DATA="http://test-dy.uzautomotors.com/api/send_data";
    public static String URL_TRANSPORT="https://b-garaj.uzautomotors.com/gosNumber/";


    public static String userHOST="US2300";
    public static String userPassword="test2300";

    public static String OmborgaYuborish="Oborga yuborish";
   // public static String KrimChiqimAdress="Oborga yuborish";
    public static String UzautoMotorsgaKirim="UzAuto kirim";
    public  static String OmborgaYuborishStatus="4";
    public  static String UzautoMotorsgaKirimStatus="7";
    public  static String OmborgaKirishStatus="5";
    public  static String OmbordanChiqim="6";

    public static String PRODACTION = "Prodaction";
    public static String PRODACTIONWEB = "ProdactionWEB";
    public static String TEST = "Test";
    final  static String IOFFLINE = "InventarOFFLINE";
    final static String OMBORGAYUBORISH="OmborgaYuborish";
    final static String OMBORGAKIRIMCHIQIM="MobileInventarizatsiya";
    final static String LINESTOP="LineStop";
    final static String VAGONGAYUKLASH="VagongaYuklash";

    //Line Stop

//    public static String URLSEND_LINE_STOP="http://10.142.4.107:8081/api/activ";
//    public static String URLSEND_LINE_STOP_LOGIN="http://10.142.4.107:8081/api/login";
//    public static String URLSEND_LINE_STOP_AREA="http://10.142.4.107:8081/api/area";
    //Line Stop
//    public static String URLSEND_LINE_STOP="http://10.142.47.98:8081/api/activ";
//    public static String URLSEND_LINE_STOP_LOGIN="http://10.142.47.98:8081/api/login";
    public static String URLSEND_LINE_STOP="http://10.142.47.98:8081/api/activ";


    public static String URLSEND_LINE_STOP_LOGIN="http://10.142.62.226:8081/api/login";
    public static String  URLSEND_LINE_STOPDATA="http://10.142.62.226:8081/api/activ/data1_2";


//    public static String URLSEND_LINE_STOP_LOGIN="http://10.142.47.98:8081/api/login";
//    public static String  URLSEND_LINE_STOPDATA="http://10.142.47.98:8081/api/activ/data1_2";



    final static String HAMMASI="HAMMASI";
    final static String SHOP="<SHOP>";
    final static String AREA="<AREA>";
    final static String DEPA="<DEPA>";
    final static String DESC="<DESC>";

    final static String ALARM_0="0";
    final static String ALARM_1="1";
    final static String KORILDMADI="0";
    final static String KORILDI="1";

    final static String FILTER="filter";



    //1-Trim, 2-ok, 3-print, 4-zavod(out), 5-Sklad(in), 6-Sklad(out) 7-Zavod(in), 8-700, 9-test
    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
