package ru.otus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.TestDao;
import ru.otus.dao.TestDaoImpl;
import ru.otus.service.IOService;
import ru.otus.service.IOServiceImpl;
import ru.otus.service.LauncherService;
import ru.otus.service.LauncherServiceImpl;

import java.io.InputStream;
import java.io.PrintStream;

@PropertySource("classpath:test.properties")
@Configuration
public class AppConfiguration {
    @Bean
    public TestDao testDao(@Value("${csv.separator}") String separator,
                           @Value("${file.name}") String fileName,
                           @Value("${pass.percentage}") int passPercentage) {
        return new TestDaoImpl(separator, fileName, passPercentage);
    }

    @Bean
    public IOService ioService(@Value("#{T(java.lang.System).in}") InputStream input,
                               @Value("#{T(java.lang.System).out}") PrintStream output) {
        return new IOServiceImpl(input, output);
    }

    @Bean
    public LauncherService launcherService(TestDao testDao, IOService ioService) {
        return new LauncherServiceImpl(testDao, ioService);
    }
}
