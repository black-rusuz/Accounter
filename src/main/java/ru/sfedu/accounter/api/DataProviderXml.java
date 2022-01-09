package ru.sfedu.accounter.api;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import ru.sfedu.accounter.Constants;
import ru.sfedu.accounter.model.Result;
import ru.sfedu.accounter.model.XmlWrapper;
import ru.sfedu.accounter.utils.ConfigurationUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProviderXml extends FileDataProvider {
    private final String XML_PATH = ConfigurationUtil.getConfigurationEntry(Constants.XML_PATH);
    private final String XML_EXTENSION = ConfigurationUtil.getConfigurationEntry(Constants.XML_EXTENSION);

    public DataProviderXml() throws IOException {
    }

    @Override
    protected <T> List<T> read(Class<T> bean) {
        List<T> list = new ArrayList<>();
        try {
            File file = initFile(classToFullFileName(XML_PATH, bean, XML_EXTENSION));
            FileReader fileReader = new FileReader(file);
            list = new Persister().read(XmlWrapper.class, fileReader).getList();
            fileReader.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

    @Override
    protected <T> Result write(List<T> list, Class<T> bean) {
        try {
            File file = initFile(classToFullFileName(XML_PATH, bean, XML_EXTENSION));
            FileWriter fileWriter = new FileWriter(file);
            Serializer serializer = new Persister();
            serializer.write(new XmlWrapper<>(list), fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            sendLogs(Constants.METHOD_NAME_WRITE, list.size() > 0 ? list.get(list.size() - 1) : null, Result.State.Error);
            return new Result(Result.State.Error, Constants.RESULT_MESSAGE_WRITING_ERROR + e.getMessage());
        }
        sendLogs(Constants.METHOD_NAME_WRITE, list.size() > 0 ? list.get(list.size() - 1) : null, Result.State.Success);
        return new Result(Result.State.Success, Constants.RESULT_MESSAGE_WRITING_SUCCESS);
    }
}
