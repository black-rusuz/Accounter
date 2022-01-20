package ru.sfedu.accounter.api;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviderCsv extends FileDataProvider {
    private final String CSV_PATH = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
    private final String CSV_EXTENSION = ConfigurationUtil.getConfigurationEntry(Constants.CSV_EXTENSION);

    public DataProviderCsv() throws IOException {
    }

    @Override
    protected <T> List<T> read(Class<T> type) {
        List<T> list = new ArrayList<>();
        try {
            File file = initFile(getName(CSV_PATH, type, CSV_EXTENSION));
            if (file.length() > 0) {
                CSVReader csvReader = new CSVReader(new FileReader(file));
                CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader).withType(type).build();
                list = csvToBean.parse();
                csvReader.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    protected <T> Result write(List<T> list, Class<T> type, String methodName) {
        try {
            File file = initFile(getName(CSV_PATH, type, CSV_EXTENSION));
            CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(list);
            csvWriter.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            sendLogs(methodName, list.size() > 0 ? list.get(list.size() - 1) : null, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(methodName, list.size() > 0 ? list.get(list.size() - 1) : null, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}
