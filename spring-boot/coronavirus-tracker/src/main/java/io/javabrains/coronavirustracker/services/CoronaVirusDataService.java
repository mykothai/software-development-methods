package io.javabrains.coronavirustracker.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CoronaVirusDataService {
    /**
     * Calls URL and provides data
     */

    private static String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @PostConstruct // when application starts, since this is a service, create instance of this class and execute this method
    public void fetchVirusData() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL)) // creates URI
                .build();

        // send request to client by taking response body and return a string
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println("http response " + httpResponse.body());

        parseCSV(httpResponse);
    }

    private void parseCSV(HttpResponse<String> httpResponse) throws IOException {
        StringReader csvBodyReader = getStringReader(httpResponse);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            String state = record.get("Province/State");
            System.out.println(state);
        }
    }

    private StringReader getStringReader(HttpResponse<String> httpResponse) {
        return new StringReader(httpResponse.body());
    }
}
