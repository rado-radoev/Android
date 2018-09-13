package com.superlamer.weatherapp;

import java.util.Map;

public interface AsyncResponse {
    void processFinish(Map<String, String> output);
}
