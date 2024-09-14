package dev.ovidio;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.jboss.resteasy.reactive.RestPath;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Path("/itens")
public class ItensResource {

    public static Map<Long, Item> items = new HashMap<>();

    private final static AtomicLong idGenerator = new AtomicLong();

    @CheckedTemplate
    static class Templates {
        static native TemplateInstance itens(Collection<Item> items);

        static native TemplateInstance itemEdit(Item item);

        static native TemplateInstance item(Item item);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance itensGet() {
        return tela();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/novo")
    public Response itensPost(@FormParam("price") BigDecimal price, @FormParam("name") String name) {
        var id = idGenerator.incrementAndGet();
        items.put(id,new Item(price,name,id));
        return telaResponse();
    }

    @GET()
    @Produces(MediaType.TEXT_HTML)
    @Path("{id}/edit")
    public TemplateInstance itensEdit(@RestPath Long id) {
        return Templates.itemEdit(items.get(id));
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Path("{id}")
    public TemplateInstance itemSave(@RestPath Long id,
                                     @FormParam("price") BigDecimal price, @FormParam("name") String name,
                                     @FormParam("action") String action) {
        if (!"cancelar".equals(action)) {
            items.put(id, new Item(price, name, id));
        }
        return Templates.item(items.get(id));
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("{id}")
    public TemplateInstance itemRecupera(@RestPath Long id) {
        return Templates.item(items.get(id));
    }

    public TemplateInstance limpaItens() {
        items.clear();
        return tela();
    }

    private static Response telaResponse() {
        return Response.status(Status.SEE_OTHER)
                .location(URI.create("/itens"))
                .build();
    }

    private static TemplateInstance tela() {
        return Templates.itens(items.values());
    }

    public static void setItems(List<Item> listItens) {
        ItensResource.items = listItens.stream()
                .collect(Collectors.toMap(Item::id, item -> item));
        Long maximoId = ItensResource.items.keySet().stream().max(Long::compareTo)
                .orElse(0L);
        idGenerator.set(maximoId);
    }

}
