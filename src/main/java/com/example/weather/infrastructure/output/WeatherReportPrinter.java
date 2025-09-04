package com.example.weather.infrastructure.output;

import com.example.weather.domain.model.WeatherData;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.List;

public class WeatherReportPrinter {

    public void generateWeatherReport(List<String> cities, List<WeatherData> dataList) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow("City", "Date", "Minimum Temperature (°C)", "Maximum Temperature (°C)", "Humidity (%)", "Wind Speed (kph)", "Wind Direction");
        table.addRule();

        for (int i = 0; i < cities.size(); i++) {
            table.addRow(buildRow(cities.get(i),dataList.get(i)));
            table.addRule();
        }
        table.setTextAlignment(TextAlignment.CENTER);
        table.getRenderer().setCWC(new CWC_LongestLine());
        table.setPaddingLeftRight(1);
        System.out.println(table.render());
    }

    private Object[] buildRow(String city, WeatherData data) {
        if (data != null) {
            return new Object[]{
                    city,
                    data.getDate(),
                    data.getMinTemp(),
                    data.getMaxTemp(),
                    data.getHumidity(),
                    data.getWindSpeed(),
                    data.getWindDir()
            };
        } else {
            return new Object[]{city, "N/A", "N/A", "N/A", "N/A", "N/A"};
        }
    }
}
