package io.javabrains.coronavirustracker.services;

import io.javabrains.coronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {
    /**
     * Makes request and provides data
     */

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct // when application starts, since this is a service, create instance of this class and execute this method
    @Scheduled(cron = "0 1 1 * * ?") // run method once in the first hour of each day
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL)) // creates URI
                .build();

        // send request to client by taking response body and return a string
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        parseCSV(httpResponse);
    }

    private void parseCSV(HttpResponse<String> httpResponse) throws IOException {
        StringReader csvBodyReader = getStringReader(httpResponse);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        setVirusData(records);
    }

    private void setVirusData(Iterable<CSVRecord> records) {
        List<LocationStats> statsList = new ArrayList<>();

        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));

            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            int prevMonthCases = Integer.parseInt(record.get(record.size() - 30));
            int prevYearCases;

            if (record.size() - 365 <= 0) {
                prevYearCases = latestCases;
            } else {
                prevYearCases = Integer.parseInt(record.get(record.size() - 363));
            }

            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases-prevDayCases);
            locationStat.setDiffFromPrevMonth(latestCases-prevMonthCases);
            locationStat.setDiffFromPrevYear(latestCases-prevYearCases);

            statsList.add(locationStat);
        }
        this.allStats = statsList;
    }



    private StringReader getStringReader(HttpResponse<String> httpResponse) {
        return new StringReader(httpResponse.body());
    }
}
