package com.yws.plane.util;

import com.yws.plane.entity.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeUtil<T> {
    public static List<Integer> companyIds(List<Company> companies) {
        List<Integer> ids = new ArrayList<>();
        for (Company company : companies) {
           ids.add(company.getCid());
        }
        return ids;
    }

    public static List<Integer> newsIds(List<News> news) {
        List<Integer> ids = new ArrayList<>();
        for (News news1 : news) {
            ids.add(news1.getId());
        }
        return ids;
    }

    public static List<Integer> planeIds(List<Plane> planes) {
        List<Integer> ids = new ArrayList<>();
        for (Plane plane : planes) {
            ids.add(plane.getId());
        }
        return ids;
    }

    public static List<Long> messageIds(List<Message> messages) {
        List<Long> ids = new ArrayList<>();
        for (Message message : messages) {
            ids.add(message.getId());
        }
        return ids;
    }

    public static List<Long> chinaFightIds(List<ChinaFight> fights) {
        List<Long> ids = new ArrayList<>();
        for (ChinaFight fight : fights) {
            ids.add(fight.getFid());
        }
        return ids;
    }
    public static List<Long> abroadFightIds(List<AbroadFight> fights) {
        List<Long> ids = new ArrayList<>();
        for (AbroadFight fight : fights) {
            ids.add(fight.getFid());
        }
        return ids;
    }

    public static List<Integer> hotCitiesIds(List<HotCity> hotCities) {
        List<Integer> ids = new ArrayList<>();
        for (HotCity city : hotCities) {
            ids.add(city.getId());
        }
        return ids;
    }
}

