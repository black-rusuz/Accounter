package ru.sfedu.accounter.api;

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

public class DataProviderCsv extends DataProviderFile implements IDataProvider {
    private final String CSV_PATH = ConfigurationUtil.getConfigurationEntry(Constants.CSV_PATH);
    private final String CSV_EXTENSION = ConfigurationUtil.getConfigurationEntry(Constants.CSV_EXTENSION);

    public DataProviderCsv() throws IOException {
    }

    @Override
    protected <T> List<T> read(Class<T> bean) {
        List<T> list = new ArrayList<>();
        try {
            File file = initFile(classToFullFileName(CSV_PATH, bean, CSV_EXTENSION));
            FileReader fileReader = new FileReader(file);
            list = new CsvToBeanBuilder<T>(fileReader).withType(bean).build().parse();
            fileReader.close();
        } catch (Exception ignored) {
        }
        return list;
    }

    @Override
    protected <T> Result write(List<T> list) {
        try {
            File file = initFile(classToFullFileName(CSV_PATH, list.get(0).getClass(), CSV_EXTENSION));
            FileWriter fileWriter = new FileWriter(file);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(fileWriter).build();
            beanToCsv.write(list);
            fileWriter.close();
        } catch (Exception e) {
            sendLogs(Constants.METHOD_NAME_WRITE, list.size() > 0 ? list.get(list.size() - 1) : null, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_WRITE, list.size() > 0 ? list.get(list.size() - 1) : null, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}
