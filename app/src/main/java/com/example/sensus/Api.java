package com.example.sensus;



public class Api {
    private static final String ROOT_URL = "http://192.168.1.24/pendudukApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";
    public static final String URL_REGISTER_USER = ROOT_URL + "registeruser";
    public static final String URL_LOGIN_USER = ROOT_URL + "login";
    public static final String URL_UPDATE_USER = ROOT_URL + "updateuser";
    public static final String URL_READ_HEROES_BY_ID = ROOT_URL + "getheroesbyid&id_pesensus=";
}
