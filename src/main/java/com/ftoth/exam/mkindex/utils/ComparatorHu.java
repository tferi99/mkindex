package com.ftoth.exam.mkindex.utils;

import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.Comparator;

public class ComparatorHu implements Comparator<String>
{
    private static final String huRules = "< a,A < á,Á < b,B < c,C < cs,Cs,CS < d,D < dz,Dz,DZ < dzs,Dzs,DZS"
            + "< e,E < é,É < f,F < g,G < gy,Gy,GY < h,H < i,I < í,Í < j,J"
            + "< k,K < l,L < ly,Ly,LY < m,M < n,N < ny,Ny,NY < o,O < ó,Ó"
            + "< ö,Ö < ő,Ő < p,P < q,Q < r,R < s,S < sz,Sz,SZ < t,T"
            + "< ty,Ty,TY < u,U < ú,Ú < ü,Ü < ű,Ű < v,V < w,W < x,X < y,Y < z,Z < zs,Zs,ZS";

    RuleBasedCollator huCollator;

    public ComparatorHu()
    {
        try {
            huCollator = new RuleBasedCollator(huRules);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compare(String s1, String s2)
    {
        return huCollator.compare(s1, s2);
    }
}
