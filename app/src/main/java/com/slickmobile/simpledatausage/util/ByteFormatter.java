package com.slickmobile.simpledatausage.util;

import java.text.NumberFormat;

public class ByteFormatter {

    private NumberFormat nf;

    public ByteFormatter(){
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
    }

    public String format(long bytes) {
        if (bytes % Math.pow(10,9) < bytes) {
            return nf.format(bytes / Math.pow(10,9)) +  " GB";
        } else if (bytes % Math.pow(10,6) < bytes) {
            return nf.format(bytes / Math.pow(10,6)) +  " MB";
        } else if (bytes % Math.pow(10,3) < bytes) {
            return nf.format(bytes / Math.pow(10,3)) +  " KB";
        } else {
            return bytes +  " B";
        }
    }
}
