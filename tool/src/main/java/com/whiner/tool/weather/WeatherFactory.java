package com.whiner.tool.weather;

public interface WeatherFactory {

    void startGetWeather(OnWeatherListener onWeatherListener);

    void stopGetWeather();

    void getWeatherSuccess(WeatherBean weather);

}
