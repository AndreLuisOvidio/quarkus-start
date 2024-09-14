package dev.ovidio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AppLifecycleBean {

    private final ObjectMapper mapper;

    @Inject
    public AppLifecycleBean(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    void onStart(@Observes StartupEvent ev) throws IOException {
        var itensFile = new File("itens.json");
        if (itensFile.isFile()) {
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Item.class);
            List<Item> listItens = mapper.readValue(itensFile, listType);
            ItensResource.setItems(listItens);
        }

    }

    void onStop(@Observes ShutdownEvent ev) throws IOException {
        Collection<Item> listItens = ItensResource.items.values();
        var itensFile = new File("itens.json");
        mapper.writeValue(itensFile, listItens);
    }
}
