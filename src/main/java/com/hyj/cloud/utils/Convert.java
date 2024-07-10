package com.hyj.cloud.utils;

public interface Convert<R,T>{
    void convert(R origin, T target);
}