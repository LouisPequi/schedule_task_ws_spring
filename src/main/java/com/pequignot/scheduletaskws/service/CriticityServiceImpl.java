package com.pequignot.scheduletaskws.service;

import com.pequignot.scheduletaskws.model.TaskHistory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("CriticityService")
public class CriticityServiceImpl implements CriticityService {

    final static private String FREQ_SPLITTER = "/";
    final static private String OCCUR_SPLITTER = "-";
    final static private long HOURS = 1000*60*60;

    /**
     * Compute frequency based on the String in DataBase
     *
     * We parse the frequencies from the String
     * Model is : X-X-X/X-X-X/X-X-X
     * '/' separate each frequency
     * '-' separate hour-day-month
     * X is an int
     *
     * We count the total hours from the frequency model
     *
     * @param freq
     * @return An array containing the 3 total hours
     */
    @Override
    public int[] getFrequencies(String freq) {
        int[] result = {0, 0, 0};
        String[] freqs = freq.split(FREQ_SPLITTER);
        for (int i = 0; i < freqs.length; i++) {
            String[] hours = freqs[i].split(OCCUR_SPLITTER);
            result[i] = Integer.parseInt(hours[0]) + 24*Integer.parseInt(hours[1]) + 30*24*Integer.parseInt(hours[2]);
        }
        return result;
    }

    @Override
    public int calculateCriticity(List<TaskHistory> history, String freq, int occur) {
        Date now = new Date();
        int[] frequencies = this.getFrequencies(freq);
        int maxCriticity = frequencies.length + 1;

        if (history.size() < occur) {
            return maxCriticity;
        }

        // If we have enough history, we just compare the oldest history
        // to calculate the criticity
        TaskHistory oldestHist = history.get(occur - 1);
        int hours = this.compareDates(now, oldestHist.getDate());

        // We return 1 if we are better than the first frequency
        // 2 if we are between 1 and 2
        // 3 if we are between 2 and 3
        // 4 else
        for (int i = 0; i < frequencies.length; i++) {
            if (hours < frequencies[i]) {
                return i + 1;
            }
        }
        return maxCriticity;
    }

    private int compareDates(Date now, Date history) {
        return (int) (now.getTime() / HOURS - history.getTime() / HOURS);
    }
}
