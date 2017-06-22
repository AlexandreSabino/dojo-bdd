# dojo-bdd

# Cenário: movimentação de estoque

<b>Dado:</b> que seu sou estoquista
<b>E:</b> estou analisando um produto
<b>Quando:</b> identificar uma perda no estoque
<b>Então:</b> desejo solicitar o decremento no estoque
<b>E:</b> o sistema deve alterar o saldo do produto.

<b>Dado:</b> que seu sou estoquista
<b>E:</b> estou analisando um produto
<b>Quando:</b> identificar um produto a mais no estoque
<b>Então:</b> desejo solicitar o incremento no estoque
<b>E:</b> o sistema deve alterar o saldo do produto
<b>E:</b> e registrar a ação efetuada.

<b>Dado:</b> que seu sou estoquista
<b>E:</b> estou analisando um produto com estoque zerado no sistema
<b>Quando:</b> identificar um produto a menos no estoque
<b>E:</b> solicitar o decremento
<b>Então:</b> o sistema deve validar se tem estoque suficiente para alterar o saldo
<b>E:</b> apresentar o erro para o usuário.

# Cenário: reserva de estoque

<b>Dado:</b> que sou um cliente
<b>Quando:</b> efetuo uma compra com apenas um produto
<b>Então:</b> o sistema deve reservar o estoque do produto com o status "RESERVADO"
<b>E:</b> e decrementar o estoque disponivel do produto

<b>Dado:</b> que sou um cliente
<b>Quando:</b> efetuo uma compra 
<b>E:</b> o produto não existe no estoque
<b>Então:</b> o sistema deve criar uma reserva com o status "SEM_ESTOQUE"
<b>E:</b> e o saldo do produto não pode ser alterado

<b>Dado:</b> que sou um faturista
<b>Quando:</b> efetuo o faturamento de um pedido
<b>Então:</b> o sistema deve alterar o status da reserva para "EFETIVADA"
<b>E:</b> baixar o estoque do produto
<b>E:</b> criar uma movimentação com os dados da nota

<b>Dado:</b> que sou um agente da central
<b>Quando:</b> o cliente solicita o cancelamento do pedido
<b>Então:</b> o sistema deve cancelar a reserva mudando o status para "CANCELADA"
<b>E:</b> e incrementar o saldo do produto