# demo Qute + hotwired turbo

Esse projeto é um teste com as ferramentas qute para template engine e hotwired turbo para fazer o gerenciamento de requisições entre o frontend e o backend.

- https://quarkus.io/guides/qute
- https://turbo.hotwired.dev/handbook/introduction

# Funcionalidade

A funcionalidade é bem simples, existe uma lista de itens onde cada item tem 3 atributos, ID, Nome e preço, em tela é possivel adicionar itens e editar itens já adicionados, todo o controle de logica é feita pelo backend.

# Implementação

A implementação foi feita usando form submite do html para manter o controle centralizado no lado do backend, nessa parte o hotwired turbo foi essencial para deixar a pagina dinamica e organizada com o backend, se você analisar o HTML pode notar que foi usado o turbo-frame, que é uma tag que a lib turbo implementar para permitir o recarregamento de uma parte especifica da tela.

Esse turbo frame foi usado em 2 momentos especficos, 1º para recarregar a lista de itens ao adicionar um novo item, 2º em cada item da lista tem o seu proprio turbo frame com seu id, e ao clicar em editar é feita uma requisição ao backend que retorna o html com os inputs de edição do item selecionado e altera somente a linha que você está editando mantendo o resto do HTML estatico.
Esse recurso do turbo frame deixa a pagina mais leve por deixar parte da tela estatica e recarregar somente elementos alterados, e também deixa a complexidade menor do que se fosse fazer requisições REST com json via javascript, pois teria a necessidade de manipular o DOM e fazer requisições fetch usand javascript nativo.