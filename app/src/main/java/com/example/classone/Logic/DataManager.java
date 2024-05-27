package com.example.classone.Logic;

import com.example.classone.Model.Country;
import com.example.classone.R;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataManager {

private static final String[] names = new String[]{
        "italy",
        "iraq",
        "bhutan",
        "kenya",
        "djibouti",
        "slovakia",
        "san_marino",
        "romania",
        "laos",
        "saudi_arabia",
        "iran",
        "czech_republic",
        "united_arab_emirates",
        "israel",
        "costa_rica",
        "philippines",
        "france",
        "argentina",
        "republic_of_macedonia",
        "canada",
    };
private static int flagImages[] = new int[]{
        R.drawable._013_italy,
        R.drawable._020_iraq,
        R.drawable._040_bhutan,
        R.drawable._067_kenya,
        R.drawable._068_djibouti,
        R.drawable._091_slovakia,
        R.drawable._097_san_marino,
        R.drawable._109_romania,
        R.drawable._112_laos,
        R.drawable._133_saudi_arabia,
        R.drawable._136_iran,
        R.drawable._149_czech_republic,
        R.drawable._151_united_arab_emirates,
        R.drawable._155_israel,
        R.drawable._156_costa_rica,
        R.drawable._192_philippines,
        R.drawable._195_france,
        R.drawable._198_argentina,
        R.drawable._236_republic_of_macedonia,
        R.drawable._243_canada,
    };
private static final boolean[] canEnter = new boolean[]{
        true,
        false,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        false,
        false,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
    };

        public static List<Country> getCountries(){
                ArrayList<Country> allCountries = new ArrayList<>();

                for (int i = 0; i < names.length; i++) {
                        allCountries.add(
                        new Country().setName(names[i])
                                .setFlagImage(flagImages[i])
                                .setCanEnter(canEnter[i])
                        );
                }
            Collections.shuffle(allCountries);
                return allCountries;
        }
}
