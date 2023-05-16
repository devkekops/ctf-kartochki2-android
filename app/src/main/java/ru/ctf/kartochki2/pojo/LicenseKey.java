package ru.ctf.kartochki2.pojo;

import com.google.gson.annotations.SerializedName;

public class LicenseKey {
    @SerializedName("licenseKey")
    public String licenseKey;

    public LicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }
}
