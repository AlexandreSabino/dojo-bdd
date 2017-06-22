# dojo-bdd

# Cenário: movimentação de estoque

<b>Dado:</b> que seu sou estoquista <br/>
<b>E:</b> estou analisando um produto <br/>
<b>Quando:</b> identificar uma perda no estoque <br/>
<b>Então:</b> desejo solicitar o decremento no estoque <br/>
<b>E:</b> o sistema deve alterar o saldo do produto. <br/>
<hr>

<b>Dado:</b> que seu sou estoquista <br/>
<b>E:</b> estou analisando um produto <br/>
<b>Quando:</b> identificar um produto a mais no estoque <br/>
<b>Então:</b> desejo solicitar o incremento no estoque <br/>
<b>E:</b> o sistema deve alterar o saldo do produto <br/>
<b>E:</b> e registrar a ação efetuada. <br/>
<hr>

<b>Dado:</b> que seu sou estoquista <br/>
<b>E:</b> estou analisando um produto com estoque zerado no sistema <br/>
<b>Quando:</b> identificar um produto a menos no estoque <br/>
<b>E:</b> solicitar o decremento <br/>
<b>Então:</b> o sistema deve validar se tem estoque suficiente para alterar o saldo <br/>
<b>E:</b> apresentar o erro para o usuário. <br/>

# Cenário: reserva de estoque

<b>Dado:</b> que sou um cliente <br/>
<b>Quando:</b> efetuo uma compra com apenas um produto <br/>
<b>Então:</b> o sistema deve reservar o estoque do produto com o status "RESERVADO" <br/>
<b>E:</b> e decrementar o estoque disponivel do produto <br/>
<hr>

<b>Dado:</b> que sou um cliente <br/>
<b>Quando:</b> efetuo uma compra <br/>
<b>E:</b> o produto não existe no estoque <br/>
<b>Então:</b> o sistema deve criar uma reserva com o status "SEM_ESTOQUE" <br/>
<b>E:</b> e o saldo do produto não pode ser alterado <br/>
<hr>

<b>Dado:</b> que sou um faturista <br/>
<b>Quando:</b> efetuo o faturamento de um pedido <br/>
<b>Então:</b> o sistema deve alterar o status da reserva para "EFETIVADA" <br/>
<b>E:</b> baixar o estoque do produto <br/>
<b>E:</b> criar uma movimentação com os dados da nota <br/>
<hr>

<b>Dado:</b> que sou um agente da central <br/>
<b>Quando:</b> o cliente solicita o cancelamento do pedido <br/>
<b>Então:</b> o sistema deve cancelar a reserva mudando o status para "CANCELADA" <br/>
<b>E:</b> e incrementar o saldo do produto <br/>
