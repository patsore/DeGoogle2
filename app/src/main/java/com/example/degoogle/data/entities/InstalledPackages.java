package com.example.degoogle.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class InstalledPackages {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "package_name")
    private String packageName;
    @ColumnInfo(name = "package_version")
    private String packageVersion;

    public InstalledPackages(@NonNull String packageName, @NonNull String packageVersion) {this.packageName = packageName; this.packageVersion = packageVersion;}

    public String getPackageName() {
        return packageName;
    }
    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageName(@NonNull String packageName) {
        this.packageName = packageName;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }
}
