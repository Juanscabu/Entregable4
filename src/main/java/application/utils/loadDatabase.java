package application.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import application.model.Cliente;
import application.model.Producto;
import application.repository.ClienteRepository;
import application.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(@Qualifier("clienteRepository") ClienteRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Cliente((long) 1234,"Seba")));
            log.info("Preloading " + repository.save(new Cliente((long) 2345, "Juan")));
        };
    }
    
    @Bean
    CommandLineRunner initDatabase2(@Qualifier("productoRepository") ProductoRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Producto((long) 1234,"Tornillo",10)));
            log.info("Preloading " + repository.save(new Producto((long) 2315, "Destornillador",400)));
        };
    }
}
